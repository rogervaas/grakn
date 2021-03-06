/*
 * GRAKN.AI - THE KNOWLEDGE GRAPH
 * Copyright (C) 2019 Grakn Labs Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package grakn.core.graph.graphdb.tinkerpop.optimize;

import grakn.core.graph.graphdb.types.system.ImplicitKey;
import org.apache.tinkerpop.gremlin.process.traversal.Compare;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.Step;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.step.filter.HasStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.filter.IsStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.filter.TraversalFilterStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.map.EdgeOtherVertexStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.map.EdgeVertexStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.map.VertexStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.sideEffect.IdentityStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.EmptyStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.HasContainer;
import org.apache.tinkerpop.gremlin.process.traversal.strategy.AbstractTraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalHelper;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

@SuppressWarnings("ComparableType")
public class AdjacentVertexFilterOptimizerStrategy extends AbstractTraversalStrategy<TraversalStrategy.ProviderOptimizationStrategy> implements TraversalStrategy.ProviderOptimizationStrategy {

    private static final AdjacentVertexFilterOptimizerStrategy INSTANCE = new AdjacentVertexFilterOptimizerStrategy();

    private AdjacentVertexFilterOptimizerStrategy() {
    }

    public static AdjacentVertexFilterOptimizerStrategy instance() {
        return INSTANCE;
    }


    @Override
    public void apply(Traversal.Admin<?, ?> traversal) {

        TraversalHelper.getStepsOfClass(TraversalFilterStep.class, traversal).forEach(originalStep -> {
            // Check if this filter traversal matches the pattern: _.inV/outV/otherV.is(x)
            Traversal.Admin<?, ?> filterTraversal = (Traversal.Admin<?, ?>) originalStep.getLocalChildren().get(0);
            List<Step> steps = filterTraversal.getSteps();
            if (steps.size() == 2 &&
                    (steps.get(0) instanceof EdgeVertexStep || steps.get(0) instanceof EdgeOtherVertexStep) &&
                    (steps.get(1) instanceof IsStep)) {
                //Get the direction in which we filter on the adjacent vertex (or null if not a valid adjacency filter)
                Direction direction = null;
                if (steps.get(0) instanceof EdgeVertexStep) {
                    EdgeVertexStep evs = (EdgeVertexStep) steps.get(0);
                    if (evs.getDirection() != Direction.BOTH) direction = evs.getDirection();
                } else {
                    direction = Direction.BOTH;
                }
                P predicate = ((IsStep) steps.get(1)).getPredicate();
                //Check that we have a valid direction and a valid vertex filter predicate
                if (direction != null && predicate.getBiPredicate() == Compare.eq && predicate.getValue() instanceof Vertex) {
                    Vertex vertex = (Vertex) predicate.getValue();

                    //Now, check that this step is preceded by VertexStep that returns edges
                    Step<?, ?> currentStep = originalStep.getPreviousStep();
                    while (currentStep != EmptyStep.instance()) {
                        if (!(currentStep instanceof HasStep) && !(currentStep instanceof IdentityStep)) {
                            break;
                        } //We can jump over other steps as we move backward
                        currentStep = currentStep.getPreviousStep();
                    }
                    if (currentStep instanceof VertexStep) {
                        VertexStep vertexStep = (VertexStep) currentStep;
                        if (vertexStep.returnsEdge()
                                && (direction == Direction.BOTH || direction.equals(vertexStep.getDirection().opposite()))) {
                            //Now replace the step with a has condition
                            TraversalHelper.replaceStep(originalStep,
                                new HasStep(traversal, new HasContainer(ImplicitKey.ADJACENT_ID.name(), P.eq(vertex))),
                                traversal);
                        }
                    }

                }
            }

        });

    }
}

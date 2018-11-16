java_test(
    name = "reasoning-it",
    size = "enormous",
    srcs = ["ReasoningIT.java"],
    test_class = "grakn.core.graql.reasoner.ReasoningIT",
    deps = [
        "//dependencies/maven/artifacts/com/google/guava",
        "//dependencies/maven/artifacts/commons-collections",
        "//dependencies/maven/artifacts/org/apache/commons:commons-math3",
        "//dependencies/maven/artifacts/org/hamcrest:hamcrest-library",
        "//server",
        "//test-integration/rule:grakn-test-server",
    ],
    resources = [
        "//test-integration/graql/reasoner/stubs:appending-rps-test",
        "//test-integration/graql/reasoner/stubs:fresh-entity-derivation-from-relations",
        "//test-integration/graql/reasoner/stubs:fresh-entity-derivation-test",
        "//test-integration/graql/reasoner/stubs:fresh-relation-derivation",
        "//test-integration/graql/reasoner/stubs:reflexive-relation",
        "//test-integration/graql/reasoner/stubs:reflexive-symmetric-relation",
        "//test-integration/graql/reasoner/stubs:resource-attachment",
        "//test-integration/graql/reasoner/stubs:resource-directionality",
        "//test-integration/graql/reasoner/stubs:resource-hierarchy",
        "//test-integration/graql/reasoner/stubs:resource-ownership",
        "//test-integration/graql/reasoner/stubs:resources-as-role-players",
        "//test-integration/graql/reasoner/stubs:test-set10",
        "//test-integration/graql/reasoner/stubs:test-set11",
        "//test-integration/graql/reasoner/stubs:test-set12",
        "//test-integration/graql/reasoner/stubs:test-set13",
        "//test-integration/graql/reasoner/stubs:test-set19",
        "//test-integration/graql/reasoner/stubs:test-set19-recursive",
        "//test-integration/graql/reasoner/stubs:test-set20",
        "//test-integration/graql/reasoner/stubs:test-set21",
        "//test-integration/graql/reasoner/stubs:test-set22",
        "//test-integration/graql/reasoner/stubs:test-set23",
        "//test-integration/graql/reasoner/stubs:test-set24",
        "//test-integration/graql/reasoner/stubs:test-set25",
        "//test-integration/graql/reasoner/stubs:test-set26",
        "//test-integration/graql/reasoner/stubs:test-set27",
        "//test-integration/graql/reasoner/stubs:test-set28",
        "//test-integration/graql/reasoner/stubs:test-set28b",
        "//test-integration/graql/reasoner/stubs:test-set29",
        "//test-integration/graql/reasoner/stubs:test-set30",
        "//test-integration/graql/reasoner/stubs:test-set7",
        "//test-integration/graql/reasoner/stubs:test-set8",
        "//test-integration/graql/reasoner/stubs:test-set9",
        "//test-integration/graql/reasoner/stubs:testSet19-recursive.gql",
        "//test-integration/graql/reasoner/stubs:type-derivation",
        "//test-integration/graql/reasoner/stubs:type-derivation-from-attribute",
        "//test-integration/graql/reasoner/stubs:type-derivation-from-relations",
        "//test-integration/graql/reasoner/stubs:type-derivation-relations-with-direct",
        "//test-integration/graql/reasoner/stubs:type-derivation-with-direct",
    ],
    classpath_resources = [
        "//test-integration/resources:logback-test",
        "//test-integration/resources:cassandra-embedded",
    ],
)

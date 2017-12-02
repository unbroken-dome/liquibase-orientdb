package org.unbrokendome.liquibase.orientdb.structure;


public enum OrientIndexType {

    UNIQUE,
    UNIQUE_HASH_INDEX,
    NOTUNIQUE,
    NOTUNIQUE_HASH_INDEX,
    FULLTEXT,
    FULLTEXT_HASH_INDEX,
    DICTIONARY,
    DICTIONARY_HASH_INDEX,
    LUCENE,
    SPATIAL
}

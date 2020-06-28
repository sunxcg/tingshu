package com.github.eprendre.sources_java_demo;

import com.github.eprendre.tingshu.sources.TingShu;
import kotlin.collections.CollectionsKt;

import java.util.List;

public final class SourceEntry {
    public static final SourceEntry INSTANCE;

    private SourceEntry() {
    }

    static {
        INSTANCE = new SourceEntry();
    }

    public static List<TingShu> getSources() {
        return CollectionsKt.listOf(M56TingShuJava.INSTANCE);
    }
}

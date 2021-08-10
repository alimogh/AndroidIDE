package com.itsaky.lsp;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class Range {
    @SerializedName("start")
    public Position start;
    
    @SerializedName("end")
    public Position end;

    public Range() {}

    public Range(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Range) {
            Range that = (Range) obj;
            return this.start.equals(that.start)
            && this.end.equals(that.end);
        }
        return false;
    }
    
    public static Range ofZero() {
        return new Range(new Position(0, 0), new Position(0, 0));
    }

    public static final Range NONE = new Range(Position.NONE, Position.NONE);
}
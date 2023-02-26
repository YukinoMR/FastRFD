package de.metanome.algorithms.dcfinder.input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import de.metanome.algorithms.dcfinder.helpers.ParserHelper;


public class Column {

    public enum Type {
        STRING, NUMERIC, LONG
    }

    private final String tableName;
    private final String name;
    private HashSet<String> valueSet = new HashSet<>();
    private List<String> values = new ArrayList<>();
    private Type type = Type.LONG;

    public Type getType() {
        if (name.contains("String"))
            return Type.STRING;
        if (name.contains("Double"))
            return Type.NUMERIC;
        if (name.contains("Integer"))
            return Type.LONG;
        return type;
    }

    public Column(String tableName, String name) {
        this.tableName = tableName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return tableName + "." + name;
    }

    public void addLine(String string) {
        if (type == Type.LONG && !ParserHelper.isInteger(string))
            type = Type.NUMERIC;
        if (type == Type.NUMERIC && !ParserHelper.isDouble(string))
            type = Type.STRING;
        valueSet.add(string);
        values.add(string);
    }

    public int size() {
        return values.size();
    }

    public Comparable<?> getValue(int line) {
        switch (type) {
            case LONG:
                return Long.parseLong(values.get(line));
            case NUMERIC:
                return Double.parseDouble(values.get(line));
            case STRING:
            default:
                return values.get(line);
        }
    }

    public Long getLong(int line) {
        return values.get(line).isEmpty() ? Long.MIN_VALUE : Long.parseLong(values.get(line));
    }

    public Double getDouble(int line) {
        return values.get(line).isEmpty() ? Double.MIN_VALUE : Double.parseDouble(values.get(line));
    }

    public String getString(int line) {
        return values.get(line) == null ? "" : values.get(line);
    }

    public int getLineCount() {
        return values.size();
    }
}
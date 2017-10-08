package com.netsgroup.jsonrole.web;

public class View {
    public interface Anonymous {}

    public interface Guest extends Anonymous {}

    public interface Organizer extends Guest {}

    public interface BusinessAdmin extends Organizer {}

    public interface TechnicalAdmin extends BusinessAdmin {}
}

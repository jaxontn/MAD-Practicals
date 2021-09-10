package curtin.edu.lab4;

public class DBSchema {

    public static class DBTable{
        public static final String NAME = "factions"; //TABLE NAME

        public static class Cols{ //COLUMNS NAMES
            public static final String ID = "faction_id";
            public static final String NAME= "faction_name";
            public static final String STR = "faction_strength";
            public static final String REL = "faction_relationship";
        }
    }
}
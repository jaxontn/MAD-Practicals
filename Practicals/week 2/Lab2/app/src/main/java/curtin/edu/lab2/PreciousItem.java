package curtin.edu.lab2;

import java.io.Serializable;

public class PreciousItem implements Serializable {

    private String[] items;
    public PreciousItem()
    {
        items = new String[]{"jade monkey",
                             "roadmap",
                             "ice scraper"
                             };
    }

    public String[] getItems()
    {
        return items;
    }
}

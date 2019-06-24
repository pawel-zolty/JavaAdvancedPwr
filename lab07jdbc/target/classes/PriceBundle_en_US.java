package main.resources;

import java.util.ListResourceBundle;

public class PriceBundle_en_US extends ListResourceBundle  {
	public Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
        { "Vulcanization_1_tire", new Double(50) },
        { "Vulcanization_2_tires", new Double(95) },
        { "Vulcanization_4_tires", new Double(175) },
        { "Tires(4)_Swap", new Double(150) },
    };
}

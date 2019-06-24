package main.resources;

import java.util.ListResourceBundle;

public class PriceBundle extends ListResourceBundle  {
	public Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
        { "Vulcanization_1_tire", new Double(200) },
        { "Vulcanization_2_tires", new Double(380) },
        { "Vulcanization_4_tires", new Double(735) },
        { "Tires(4)_Swap", new Double(550) },
    };
}

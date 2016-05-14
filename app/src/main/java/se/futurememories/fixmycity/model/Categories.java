package se.futurememories.fixmycity.model;

import java.util.ArrayList;

/**
 * Created by Mattias on 14/05/16.
 */
public class Categories {

    public static ArrayList<String> GetCategoriesForType(String type) {
        ArrayList<String> categories = new ArrayList<>();

        switch (type) {
            case "Problem":
                categories.add("Badplats");
                categories.add("Buller");
                categories.add("Belysning");
                categories.add("Cykelbana");
                categories.add("Gatuunderhåll");
                categories.add("Gräs");
                categories.add("Hål i gatan");
                categories.add("Lekplats");
                categories.add("Klotter");
                categories.add("Nedskräpning");
                categories.add("Offentliga toaletter");
                break;
            default:
                categories.add("Hål i gatan");
                categories.add("Lekplats");
                categories.add("Klotter");
                break;
        }
        return categories;
    }
}

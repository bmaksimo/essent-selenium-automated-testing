package com.essent.testing.dwp.helper;

import java.util.Random;

public class AddressUtil {

    private static String[] streets = {
            "Abelenlaan", "Abraham Hanslaan", "Albertlei", "Albrecht Rodenbachlaan", "Alfsberg", "Altenastraat", "Antwerpsesteenweg", "Asterlaan", "Astweg", "Azalealaan", "Baanvelden", "Babbelkroonstraat", "Bautersemstraat", "Beeklaan", "Beekse Velden", "Beemdenlaan", "Bergstraat", "Beukendreef", "Biesaard", "Binnenbeemd", "Blauwesteenstraat", "Bochtstraat", "Boniverlei", "Boomgaard", "Boskapelweg", "Bosveldlaan", "Broekbosstraat", "Brouwersstraat", "Brugmanstraat", "Cornelis Marckxlaan", "Cornelis Verhulstlaan", "De Villermontstraat", "Deken Jozef Van Herckstraat", "Dennenlaan", "Doelveld", "Doopput", "Doornstraat", "Dorre Eikstraat", "Drabstraat", "Dries", "Duffelsesteenweg", "Duffelshoek", "Duivenstraat", "Edegemsesteenweg", "Edgard Tinellaan", "Eekhoven", "Eertbrugge", "Eikenstraat", "Elisabethstraat", "Elsbos", "Elshagelaan", "Gallo-Romeinenlaan", "Ganzenbollaan", "Gemeenteplein", "Graaf de Ribeaucourtplein", "Groene Wandeling", "Groene Weg", "Groeningenlei", "Groot Veld", "Haakstuk", "Heiveldekens", "Helenaveldstraat", "Hoeve-ter-Bekelaan", "Hof van Spruytlaan", "Hofstraat", "Hoge Akker", "Holle Eikaard", "Holle Weg", "Hondstraat", "Hoogbunderlaan", "Hoogmolenlaan", "Ijzermaalberg", "Infanterielaan", "Irislaan", "Jan-Baptist Reykerslaan", "Jeroen en Peter Conventlaan", "Jordaensstraat", "Joris Olyslaegerslaan", "Josephine Charlottestraat", "Josse Clymansstraat", "Kapelstraat", "Kartuizersweg", "Kattenbroek", "Kauwlei", "Kazernelaan", "Keizershoek", "Keltenveld", "Kerkeland", "Kleine Meylstraat", "Klokkestraat", "Kongostraat", "Konijnenveld", "Koningin Astridlaan", "Koningin Fabiolalaan", "Kontichhof", "Kosterijstraat", "Kruisbeemd", "Kruisschanslei", "Kruisstraat", "Langbosweg", "Leopoldstraat", "Liersebaan", "Lijsterbolstraat", "Lints Veld", "Lintsesteenweg", "Magdalenastraat", "Mechelsesteenweg", "Metsersaard", "Meylweg", "Mina Telghuislaan", "Molenstraat", "Montfortstraat", "Moorstraat", "Mortelstuk", "Nachtegaalstraat", "Nakkersgoed", "Neerveld", "Nerenaard", "Nieuwstraat", "Noordstraat", "Oever", "Ooststatiestraat", "Oude Lei", "Pastoor De Laetstraat", "Pauwhoevestraat", "Peter Benoitlaan", "Philip Romboutslaan", "Pierstraat", "Pieter Potlaan", "Pluyseghemstraat", "Prins Boudewijnlaan", "Prins Filiplaan", "Pronkenbergstraat", "Rauwaard", "Reepkenslei", "Reetsestraat", "Reipelveld", "Rijkerooistraat", "Rompelei", "Roosken", "Rozengaard", "Rubensstraat", "Satenrozen", "Scheihagenstraat", "Schoolstraat", "Schuttershofstraat", "Schuurveld", "Singel", "Sint-Jansplein", "Sint-Martinusplein", "Sint-Martinusstraat", "Sleutelstraat", "Spoorwegstraat", "Staf Van Elzenlaan", "Stationsplein", "Steenakker", "Steentjeslaan", "Strepestraat", "Tanghoflaan", "Ter Sneeuw", "Transvaalstraat", "Tulpenlaan", "Twee Bunder", "Valveken", "Van Dyckstraat", "Varkensmarkt", "Vekenveld", "Veldkant", "Verbrande Hoevestraat", "Vijverlaan", "Vitsenveld", "Vlierenpaal", "Volderij", "Vredestraat", "Wierookstuk", "Wild Veld", "Wilgstuk", "Wipstraat", "Wisselbeemd", "Witte-Stedeweg", "Witvrouwenveldstraat", "Zilverbergstraat",
        };

    public static String getRandomStreet() {

        Random rnd = new Random();

        int index = (int) (rnd.nextFloat() * streets.length);

        return streets[index];
    }
}

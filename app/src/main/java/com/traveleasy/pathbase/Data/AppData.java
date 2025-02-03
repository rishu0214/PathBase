package com.traveleasy.pathbase.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.traveleasy.pathbase.Constants.MyConstants;
import com.traveleasy.pathbase.Database.RoomDBB;
import com.traveleasy.pathbase.Model.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDBB database;
    String category;
    Context context;

    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 3;

    public AppData(RoomDBB database){
        this.database = database;
    }

    public AppData(RoomDBB database, Context context) {
        this.database = database;
        this.context = context;
    }


    public List<Items> getBasicData () {
        category="Basic Needs";
        List<Items> basicItem = new ArrayList<Items>();
        basicItem.clear();
        basicItem.add(new Items("Visa", category, false));
        basicItem.add(new Items("Passport", category, false));
        basicItem.add(new Items("Tickets", category, false));
        basicItem.add(new Items("Wallet", category, false));
        basicItem.add(new Items("Driving License", category, false));
        basicItem.add(new Items("Currency", category, false));
        basicItem.add(new Items("House Key", category, false));
        basicItem.add(new Items("Book", category, false));
        basicItem.add(new Items("Travel Pillow", category, false));
        basicItem.add(new Items("Luggage Locks", category, false));
        basicItem.add(new Items("Sunglasses", category, false));
        basicItem.add(new Items("Hand Sanitizer", category, false));

        return basicItem;
    }


    public List<Items> getPersonalCareData() {

        String[] data = {
                "Toothbrush",
                "Toothpaste",
                "Floss",
                "Mouthwash",
                "Shaving Cream",
                "Razor",
                "Soap or Body Wash",
                "Shampoo",
                "Conditioner",
                "Hair Brush",
                "Comb",
                "Hair Dryer",
                "Curling Iron or Straightener",
                "Hair Gel or Mousse",
                "Hair Clips or Bands",
                "Face Moisturizer",
                "Body Lotion",
                "Lip Balm",
                "Deodorant",
                "Makeup Essentials",
                "Wet Wipes",
                "Travel Laundry Soap",
        };

        return prepareItemsList (MyConstants.PERSONAL_CARE_CAMEL_CASE, data);
    }

    public List<Items> getClothingData() {
        String[] data = {
                "Underwear",
                "Socks or Stockings",
                "Pajamas",
                "T-Shirts",
                "Casual Shirt",
                "Sweater or Cardigan",
                "Light Jacket",
                "Heavy Jacket or Coat",
                "Trousers or Jeans",
                "Shorts",
                "Casual Dress",
                "Bikini",
                "Raincoat",
                "Hat or Cap",
                "Scarf",
                "Gloves",
                "Comfortable Walking Shoes",
                "Sandals or Slippers",
                "Belt"
        };

        return prepareItemsList (MyConstants.CLOTHING_CAMEL_CASE, data);
    }

    public List<Items> getBabyNeedsData() {
        String[] data = {
                "Diapers",
                "Baby Wipes",
                "Changing Mat",
                "Diaper Cream",
                "Baby Bottles",
                "Formula or Baby Food",
                "fruits",
                "Pacifiers",
                "Baby Blanket",
                "Baby Clothes",
                "Baby Socks",
                "Hat (for Sun or Warmth)",
                "Baby Carrier or Sling",
                "Stroller",
                "Car Seat",
                "Toys or Comfort Items",
                "Baby Bibs",
                "Burp Cloths",
                "First Aid Kit (Baby-Friendly)"
        };

        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data);
    }
public List<Items> getHealthData() {
    String[] data = {
            "Prescription Medications",
            "Paracetamol",
            "Antihistamines (for Allergies)",
            "Antacids (for Indigestion)",
            "Anti-diarrheal Medication",
            "Motion Sickness Tablets",
            "Cold and Flu Medication",
            "Thermometer",
            "Bandages",
            "Antiseptic Cream",
            "Tweezers",
            "Medical Tape",
            "Gauze Pads",
            "Insect Repellent",
            "Sunscreen",
            "Lip Balm with SPF",
            "Hand Sanitizer",
            "First Aid Manual (Travel Size)",
            "Vitamins or Supplements"
    };

    return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE, data);
}
public List<Items> getTechnologyData () {
    String[] data = {
            "Smartphone",
            "Phone Charger",
            "Power Bank",
            "Laptop",
            "Tablet",
            "Laptop Charger",
            "Earbuds",
            "Bluetooth Speaker",
            "Portable Wi-Fi Hotspot",
            "Power Adapter",
            "Camera",
            "Camera Charger",
            "External Hard Drive",
            "Smartwatch",
            "E-Reader",
            "Memory Cards",
            "Noise-Cancelling Headphones",
            "Smartphone Stand or Tripod",
            "Cord Organizer or Pouch"
    };

    return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE, data);
}

    public List<Items> getFoodData() {
        String[] data = {
                "Granola Bars",
                "Trail Mix",
                "Dried Fruit",
                "Fresh Fruit",
                "Crackers",
                "Rice Cakes",
                "Peanut Butter",
                "Protein Bars",
                "Instant Oatmeal Packets",
                "Cheese Sticks or Cubes",
                "Cereal",
                "Instant Soup Packets",
                "Bread and Butter",
                "Jam",
                "Veggie Chips or Popcorn",
                "Cookies",
                "Chocolate or Energy Bites",
                "Water Bottles"
        };

        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE, data);
    }
    public List<Items> getBeachSuppliesData() {
        String[] data = {
                "Trekking Boots",
                "Socks",
                "Backpack",
                "Water Bottle",
                "Trekking Poles",
                "Headlamp or Flashlight",
                "Extra Batteries",
                "Multi-tool",
                "First Aid Kit",
                "Sunscreen",
                "Swiss Army Knife",
                "Hats (for Sun or Warmth)",
                "Sunglasses",
                "Jacket (Rain or Windproof)",
                "Thermal Layer",
                "Quick-Dry Clothes",
                "Energy Snacks",
                "Trekking Map or GPS Device",
                "Personal Identification",
                "Lightweight Sleeping Bag"
        };

        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data);
    }
    public List<Items> getCarSuppliesData () {
        String[] data = {
                "Spare Tire",
                "Car Jack",
                "Jumper Cables",
                "Tire Pressure Gauge",
                "Car Battery Charger",
                "First Aid Kit",
                "Flashlight",
                "Road Flares",
                "Multi-tools",
                "Duct Tape",
                "Vehicle Documents",
                "Car Manual",
                "Windshield Washer Fluid",
                "Car Phone Mount",
                "Portable Air Compressor",
                "Seatbelt Cutter",
                "Swiss Army Knife",
                "Window Breaker Tool",
                "Emergency Blanket",
        };

        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data);
    }
        public List<Items> getNeedsData() {
            String[] data = {
                    "Backpack",
                    "Laundry Bag",
                    "Sewing Kit",
                    "Travel Lock",
                    "Luggage Tag",
                    "Magazine or Book",
                    "Sports Equipment",
                    "Important Numbers",
                    "Passport Holder",
                    "Travel Wallet",
                    "Emergency Contact List",
                    "Travel Pillow",
                    "Reusable Shopping Bag",
                    "Travel Guide or Map",
                    "Sunglasses Case",
            };

            return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE, data);
        }
//.................................................//
    public List<Items> prepareItemsList(String category, String []data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();

        for (int i=0; i< list.size(); i++){
            dataList.add(new Items(list.get(i), category, false ));
        }
        return dataList;
    }

    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getNeedsData());

        return listOfAllItems;
    }

    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for (List<Items> list: listOfAllItems){
            for(Items items : list){
                database.mainDao().saveItem(items);
            }
        }
        System.out.println("Data added.");
    }

    public void persistDataByCategory(String category, Boolean onlyDelete){
        try{
            List<Items> list = deleteAndGetListByCategory(category, onlyDelete);

            if (!onlyDelete){
                for (Items item : list){
                    database.mainDao().saveItem(item);
                }
                Toast.makeText(context, category + " Reset Successfully...", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, category + " Deleted Successfully...", Toast.LENGTH_SHORT).show();
            }
            
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Items> deleteAndGetListByCategory(String category, Boolean onlyDelete){
        if(onlyDelete){
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        }else {
            database.mainDao().deleteAllByCategory(category);
        }
        switch (category){
            case MyConstants.BASIC_NEEDS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();

            case MyConstants.PERSONAL_CARE_CAMEL_CASE:
                return getPersonalCareData();

            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBabyNeedsData();

            case MyConstants.HEALTH_CAMEL_CASE:
                return getHealthData();

            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();

            case MyConstants.FOOD_CAMEL_CASE:
                return getFoodData();

            case MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBeachSuppliesData();

            case MyConstants.CAR_SUPPLIES_CAMEL_CASE:
                return getCarSuppliesData();

            case MyConstants.NEEDS_CAMEL_CASE:
                return getNeedsData();

            default:
                return new ArrayList<>();
        }
    }

}

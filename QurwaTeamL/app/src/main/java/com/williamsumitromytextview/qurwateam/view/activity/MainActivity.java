package com.williamsumitromytextview.qurwateam.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.williamsumitromytextview.qurwateam.R;
import com.williamsumitromytextview.qurwateam.model.database.DatabaseHelper;
import com.williamsumitromytextview.qurwateam.model.entity.Berita;
import com.williamsumitromytextview.qurwateam.model.entity.ChatMessage;
import com.williamsumitromytextview.qurwateam.model.entity.DaftarFranchise;
import com.williamsumitromytextview.qurwateam.model.entity.EventClass;
import com.williamsumitromytextview.qurwateam.model.entity.Outlet;
import com.williamsumitromytextview.qurwateam.model.entity.User;
import com.williamsumitromytextview.qurwateam.model.session.SessionManagement;
import com.williamsumitromytextview.qurwateam.view.fragment.About_UsFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.BookmarkFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.How_It_WorksFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.PrivacyFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.Rate_Our_AppFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.SearchFragment;
import com.williamsumitromytextview.qurwateam.view.fragment.TabFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william on 10/03/2017.
 */
//implements MaterialSearchView.OnQueryTextListener

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navigationview) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private MenuItem activeMenuItem;
    private int CurrentSelectedPosition;
    SessionManagement session;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static DatabaseHelper databaseHelper;

    DatabaseHelper databasehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        initUsers();
        initFranchise();
        initEvent();
        initBerita();
        initOutlet();
        initMessage();


        session = new SessionManagement(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        String email = user.get(SessionManagement.KEY_EMAIL);
        String name = user.get(SessionManagement.KEY_NAME);


        View header = navigationView.getHeaderView(0);
        TextView tvEmail = (TextView) header.findViewById(R.id.UserEmail);
        TextView tvNama = (TextView) header.findViewById(R.id.UserName);
        tvEmail.setText(email);
        tvNama.setText(name);

        //membuat fragment tab menjadi fragment pertama
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerview, new TabFragment()).commit();

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditUser.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(activeMenuItem != null){
                    activeMenuItem.setChecked(false);
                }
                activeMenuItem = menuItem;
                drawerLayout.closeDrawers();
                menuItem.setChecked(true);
                switch (menuItem.getItemId()){
                    case R.id.nav_item_home:
                        CurrentSelectedPosition = 0;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerview, new TabFragment()).commit();
                        break;
                    case R.id.nav_item_bookmark:
                        CurrentSelectedPosition = 1;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerview, new BookmarkFragment()).commit();
                        break;
                    case R.id.nav_item_search:
                        CurrentSelectedPosition = 2;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerview, new SearchFragment()).commit();
                        break;
                    case R.id.nav_item_about_us:
                        CurrentSelectedPosition = 3;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerview, new About_UsFragment()).commit();
                        break;
                    case R.id.nav_item_rate_our_ap:
                        CurrentSelectedPosition = 4;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerview, new Rate_Our_AppFragment()).commit();
                        break;
                    case R.id.nav_item_terms_of_use:
                        CurrentSelectedPosition = 5;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerview, new PrivacyFragment()).commit();
                        break;
                    case R.id.nav_item_how_it_works:
                        CurrentSelectedPosition = 6;
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerview, new How_It_WorksFragment()).commit();
                        break;
                    case R.id.nav_logout:
                        session.logoutUser();
                        finish();
                        break;
                    default:
                        break;
                }
                return false;
            }

        });
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
    public void initUsers(){
        databasehelper = new DatabaseHelper(MainActivity.this);
        User user1 = new User();
        user1.setEmail("Anggi@gmail.com");
        user1.setName("Anggi");
        user1.setPassword("Anggi@123");
        databasehelper.addUser(user1);

        User user2 = new User();

        user2.setEmail("Betta@gmail.com");
        user2.setName("Betta");
        user2.setPassword("Betta@123");
        databasehelper.addUser(user2);

        User user3 = new User();

        user3.setEmail("Banty@gmail.com");
        user3.setName("Banty");
        user3.setPassword("Banty@123");
        databasehelper.addUser(user3);

        User user4 = new User();

        user4.setEmail("Curve@gmail.com");
        user4.setName("Curve");
        user4.setPassword("Curve@123");
        databasehelper.addUser(user4);

        User user5 = new User();

        user5.setEmail("Keke@gmail.com");
        user5.setName("Keke");
        user5.setPassword("Keke@123");
        databasehelper.addUser(user5);
    }
    public void initFranchise() {
        databasehelper = new DatabaseHelper(MainActivity.this);

        DaftarFranchise Pizza_hut = new DaftarFranchise();
        Pizza_hut.setId(1);
        Pizza_hut.setNama_franchise("Pizza Hut");
        Pizza_hut.setNama_pt_franchisor("Pizza Hut, Inc");
        Pizza_hut.setKeterangan("The franchisor, Pizza Hut, LLC (PHLLC or Pizza Hut), operates and franchises pizza restaurants. PHLLC’s primary activity is the operation, development, franchising, and servicing of single-purpose Pizza Hut restaurants, which offer various combinations of family dine-in, carryout, and delivery services, primarily serving pizza, pasta, and other Italian-style food items. PHLLC’s corporate parent is YUM! Brands, Inc.");
        Pizza_hut.setJenis("Asing");
        Pizza_hut.setKategori("Makanan");
        Pizza_hut.setBerdiri_sejak("2015");
        Pizza_hut.setInvestasi(2144000);
        Pizza_hut.setWebsite("www.Pizza_hut.co.id");
        Pizza_hut.setAlamat("Jl Thamrin no 12");
        Pizza_hut.setLokasi("Sumut");
        Pizza_hut.setTelepon("7312322");
        Pizza_hut.setEmail("Pizza_hut@outlook.net");
        Pizza_hut.setGambar_franchise("banner1.jpg");
        databasehelper.addFranchise(Pizza_hut);

        DaftarFranchise KFC = new DaftarFranchise();
        KFC.setId(2);
        KFC.setNama_franchise("Kentucky Fried Chicken");
        KFC.setNama_pt_franchisor("KFC, Inc");
        KFC.setKeterangan("KFC outlets prepare and sell chicken, snackables and other approved menu items using the certain trademarks and trade secrets owned by KFC Corporation. The franchisor is KFC Corporation (KFCLLC) whose parent is YUM! Brands, Inc.");
        KFC.setJenis("Asing");
        KFC.setKategori("Makanan");
        KFC.setBerdiri_sejak("2010");
        KFC.setInvestasi(2576000);
        KFC.setWebsite("www.kfc.co.id");
        KFC.setAlamat("Jl ABC");
        KFC.setLokasi("Sumut");
        KFC.setTelepon("7312345");
        KFC.setEmail("kfc@outlook.net");
        KFC.setGambar_franchise("banner2.jpg");
        databasehelper.addFranchise(KFC);

        DaftarFranchise McDonalds = new DaftarFranchise();
        McDonalds.setId(3);
        McDonalds.setNama_franchise("McDonalds");
        McDonalds.setNama_pt_franchisor("McDonalds Inc");
        McDonalds.setKeterangan("McDonald’s USA, LLC is the franchisor. The franchisor develops, operates, franchises, and services a system of restaurants that prepare, assemble, package, and sell a limited menu of value-priced foods under the “McDonald's System.");
        McDonalds.setJenis("Asing");
        McDonalds.setKategori("Makanan");
        McDonalds.setBerdiri_sejak("2012");
        McDonalds.setInvestasi(2124080);
        McDonalds.setWebsite("www.McDonalds.co.id");
        McDonalds.setAlamat("Jl susi tei no 12");
        McDonalds.setLokasi("Sumut");
        McDonalds.setTelepon("7312322");
        McDonalds.setEmail("McDonalds@outlook.net");
        McDonalds.setGambar_franchise("banner3.jpg");
        databasehelper.addFranchise(McDonalds);


        DaftarFranchise Starbucks = new DaftarFranchise();
        Starbucks.setId(4);
        Starbucks.setNama_franchise("Starbucks");
        Starbucks.setNama_pt_franchisor("PT Starbucks");
        Starbucks.setKeterangan("lezat");
        Starbucks.setJenis("Asing");
        Starbucks.setKategori("Makanan dan minuman");
        Starbucks.setBerdiri_sejak("2010");
        Starbucks.setInvestasi(2654600);
        Starbucks.setWebsite("www.Starbucks.co.id");
        Starbucks.setAlamat("Jl Thamrin no 122");
        Starbucks.setLokasi("Sumut");
        Starbucks.setTelepon("7313322");
        Starbucks.setEmail("Starbucks@outlook.net");
        Starbucks.setGambar_franchise("banner4.jpg");
        databasehelper.addFranchise(Starbucks);

        DaftarFranchise _7Eleven = new DaftarFranchise();
        _7Eleven.setId(5);
        _7Eleven.setNama_franchise("7 Eleven");
        _7Eleven.setNama_pt_franchisor("PT SEVEN SEVEN GO");
        _7Eleven.setKeterangan("7-Eleven stores are extended-hour retail convenience stores that emphasize convenience to the guest and provide a broad array of products, including many not traditionally available in convenience stores, to meet the changing needs of guests. These products include an assortment of high-quality fresh food, hot food and proprietary beverage offerings, and private brand items. 7-Eleven stores are generally open every day of the year, with the vast majority open 24 hours a day, and are located in neighborhood areas, on main thoroughfares, in shopping centers, or on other sites where they are easily accessible and have parking facilities for quick in-and-out shopping.");
        _7Eleven.setJenis("Asing");
        _7Eleven.setKategori("Minuman");
        _7Eleven.setBerdiri_sejak("2011");
        _7Eleven.setInvestasi(1122100);
        _7Eleven.setWebsite("www._7Eleven.co.id");
        _7Eleven.setAlamat("Jl Sutomo no 122");
        _7Eleven.setLokasi("Sumut");
        _7Eleven.setTelepon("4141322");
        _7Eleven.setEmail("_7Eleven@outlook.net");
        _7Eleven.setGambar_franchise("banner5.jpg");
        databasehelper.addFranchise(_7Eleven);

        DaftarFranchise subway = new DaftarFranchise();
        subway.setId(6);
        subway.setNama_franchise("Subway");
        subway.setNama_pt_franchisor("Subway Inc");
        subway.setKeterangan("The franchisor is Doctor Associates Inc. Subway Restaurants sell foot-long and other sandwiches, salads, and other food items from a retail store");
        subway.setJenis("Asing");
        subway.setKategori("Makanan");
        subway.setBerdiri_sejak("1974");
        subway.setInvestasi(1400000000);
        subway.setWebsite("www.subway.co.id");
        subway.setAlamat("Miami Springs, Florida");
        subway.setLokasi("United States");
        subway.setTelepon("0614145001");
        subway.setEmail("subway@outlook.net");
        subway.setGambar_franchise("banner6.jpg");
        databasehelper.addFranchise(subway);

        DaftarFranchise WyndhamHotelsandResorts = new DaftarFranchise();
        WyndhamHotelsandResorts.setId(7);
        WyndhamHotelsandResorts.setNama_franchise("Wyndham Hotels");
        WyndhamHotelsandResorts.setNama_pt_franchisor("Wyndham Hotels and Resorts Group");
        WyndhamHotelsandResorts.setKeterangan("Wyndham Hotel Group, part of the Wyndham Worldwide family of companies, hotels under 14 brands: Wyndham Hotels and Resorts, Ramada, Days Inn, Super 8, Wingate by Wyndham, Baymont Inn & Suites, Microtel Inns and Suites by Wyndham, Hawthorn Suites by Wyndham, TRYP by Wyndham, Howard Johnson, Travelodge Knights Inn, Dream Hotels and Night Hotels. In addition, the company has a license agreement to franchise the Planet Hollywood Hotels brand. Wyndham Worldwide’s lodging business began operations in 1990 with the acquisition of the Howard Johnson and Ramada brands, each of which opened its first hotel in 1954. Wyndham Worldwide family of companies also includes Wyndham Exchange & Rentals and Wyndham Vacation Ownership. Wyndham Worldwide spun off from Cendant Corp. in 2006.");
        WyndhamHotelsandResorts.setJenis("Asing");
        WyndhamHotelsandResorts.setKategori("Hotel");
        WyndhamHotelsandResorts.setBerdiri_sejak("1990");
        WyndhamHotelsandResorts.setInvestasi(850000000);
        WyndhamHotelsandResorts.setWebsite("www.WyndhamHotelsandResorts.co.id");
        WyndhamHotelsandResorts.setAlamat("Parsippany");
        WyndhamHotelsandResorts.setLokasi("New Jersey");
        WyndhamHotelsandResorts.setTelepon("0614145002");
        WyndhamHotelsandResorts.setEmail("WyndhamHotelsandResorts@outlook.net");
        WyndhamHotelsandResorts.setGambar_franchise("banner7.jpg");
        databasehelper.addFranchise(WyndhamHotelsandResorts);

        DaftarFranchise InterContinentalHotelsandResorts = new DaftarFranchise();
        InterContinentalHotelsandResorts.setId(8);
        InterContinentalHotelsandResorts.setNama_franchise("InterContinental Hotels");
        InterContinentalHotelsandResorts.setNama_pt_franchisor("InterContinental Hotels & Resorts Group");
        InterContinentalHotelsandResorts.setKeterangan("The franchisor is Holiday Hospitality Franchising, LLC (HHFL). InterContinental Hotels are typically located in major markets, important secondary cities and resort destinations. InterContinental Resorts are located in resort destinations. InterContinental Hotels and InterContinental Resorts are full service facilities targeted to discriminating business, conference and leisure travelers. HHFL also offers franchises for the Staybridge Suites, Holiday Inn, Holiday Inn Express and Crowne Plaza hotel brand groups in the United States and Canada.");
        InterContinentalHotelsandResorts.setJenis("Asing");
        InterContinentalHotelsandResorts.setKategori("Hotel");
        InterContinentalHotelsandResorts.setBerdiri_sejak("1956");
        InterContinentalHotelsandResorts.setInvestasi(98487600);
        InterContinentalHotelsandResorts.setWebsite("www.InterContinentalHotelsandResorts.co.id");
        InterContinentalHotelsandResorts.setAlamat("Atlanta");
        InterContinentalHotelsandResorts.setLokasi("England");
        InterContinentalHotelsandResorts.setTelepon("0614145003");
        InterContinentalHotelsandResorts.setEmail("InterContinentalHotelsandResorts@outlook.net");
        InterContinentalHotelsandResorts.setGambar_franchise("banner8.jpg");
        databasehelper.addFranchise(InterContinentalHotelsandResorts);

        DaftarFranchise HiltonHotelsandResorts = new DaftarFranchise();
        HiltonHotelsandResorts.setId(9);
        HiltonHotelsandResorts.setNama_franchise("Hilton Hotels & Resorts");
        HiltonHotelsandResorts.setNama_pt_franchisor("Hilton Hotels & Resorts Group");
        HiltonHotelsandResorts.setKeterangan("Hilton Franchise Holding LLC is the franchisor. The ultimate corporate parent is Hilton Worldwide Holdings, Inc. (formerly, Hilton Hotels Corporation). The company owns, manages or franchises a portfolio of brands, including Hilton Hotels & Resorts, Conrad Hotels, Doubletree, Embassy Suites Hotels, Hampton Inn, Hampton Inn & Suites, Hilton Garden Inn, Hilton Grand Vacations Company, Homewood Suites by Hilton, Home2 Suites by Hilton and Waldorf Astoria.");
        HiltonHotelsandResorts.setJenis("asing");
        HiltonHotelsandResorts.setKategori("Hotel");
        HiltonHotelsandResorts.setBerdiri_sejak("1965");
        HiltonHotelsandResorts.setInvestasi(95746621);
        HiltonHotelsandResorts.setWebsite("www.HiltonHotelsandResorts.co.id");
        HiltonHotelsandResorts.setAlamat("McLean");
        HiltonHotelsandResorts.setLokasi("Virginia");
        HiltonHotelsandResorts.setTelepon("0614145004");
        HiltonHotelsandResorts.setEmail("HiltonHotelsandResorts@outlook.net");
        HiltonHotelsandResorts.setGambar_franchise("banner9.jpg");
        databasehelper.addFranchise(HiltonHotelsandResorts);

        DaftarFranchise MarriottInternational = new DaftarFranchise();
        MarriottInternational.setId(10);
        MarriottInternational.setNama_franchise("Marriott International");
        MarriottInternational.setNama_pt_franchisor("Marriott International Inc");
        MarriottInternational.setKeterangan("Marriott International, Inc. is the franchisor. Marriott International is a worldwide operator and franchisor of hotels and related lodging facilities. Marriott International was formed in 1992 when Marriott Corporation split into two companies, Marriott International and Host Marriott Corporation.");
        MarriottInternational.setJenis("asing");
        MarriottInternational.setKategori("Hotel");
        MarriottInternational.setBerdiri_sejak("1967");
        MarriottInternational.setInvestasi(103604490);
        MarriottInternational.setWebsite("www.MarriottInternational.co.id");
        MarriottInternational.setAlamat("Bethesda");
        MarriottInternational.setLokasi("Maryland");
        MarriottInternational.setTelepon("0614145005");
        MarriottInternational.setEmail("MarriottInternational@outlook.net");
        MarriottInternational.setGambar_franchise("banner10.jpg");
        databasehelper.addFranchise(MarriottInternational);

        DaftarFranchise DominosPizza = new DaftarFranchise();
        DominosPizza.setId(11);
        DominosPizza.setNama_franchise("Domino's Pizza");
        DominosPizza.setNama_pt_franchisor("Domino's Pizza Inc");
        DominosPizza.setKeterangan("The franchisor, Domino’s Pizza Franchising LLC, offers Traditional, Non-Traditional and Transitional Domino's Pizza Store concepts under which the franchisee will operate a Domino's Pizza Store selling pizza and other authorized products through delivery and carry-out services.");
        DominosPizza.setJenis("asing");
        DominosPizza.setKategori("Makanan dan minuman");
        DominosPizza.setBerdiri_sejak("1967");
        DominosPizza.setInvestasi(535900);
        DominosPizza.setWebsite("www.DominosPizza.co.id");
        DominosPizza.setAlamat("Ann Arbor");
        DominosPizza.setLokasi("Michigan");
        DominosPizza.setTelepon("0614145006");
        DominosPizza.setEmail("DominosPizza@outlook.net");
        DominosPizza.setGambar_franchise("banner11.jpg");
        databasehelper.addFranchise(DominosPizza);

        DaftarFranchise GroupeCasino = new DaftarFranchise();
        GroupeCasino.setId(12);
        GroupeCasino.setNama_franchise("Groupe Casino");
        GroupeCasino.setNama_pt_franchisor("Groupe Casino, Inc");
        GroupeCasino.setKeterangan("Groupe Casino is a food retailer / Supermarket");
        GroupeCasino.setJenis("asing");
        GroupeCasino.setKategori("Supermarket");
        GroupeCasino.setBerdiri_sejak("1948");
        GroupeCasino.setInvestasi(55000);
        GroupeCasino.setWebsite("www.GroupeCasino.co.id");
        GroupeCasino.setAlamat("Saint-Étienne");
        GroupeCasino.setLokasi("France");
        GroupeCasino.setTelepon("0614145012");
        GroupeCasino.setEmail("GroupeCasino@outlook.net");
        GroupeCasino.setGambar_franchise("banner12.jpg");
        databasehelper.addFranchise(GroupeCasino);

        DaftarFranchise JaniKing = new DaftarFranchise();
        JaniKing.setId(13);
        JaniKing.setNama_franchise("Jani-King Franchise");
        JaniKing.setNama_pt_franchisor("Jani-King Franchise Group");
        JaniKing.setKeterangan("The franchisor, Jani-King Franchising, Inc. (JKF), is in the business of franchising comprehensive commercial cleaning and maintenance businesses. The franchise is for a business that will sell and support franchise businesses that will, in turn, provide comprehensive, commercial cleaning and maintenance services. JKF is a wholly owned subsidiary of Jani-King International, Inc");
        JaniKing.setJenis("asing");
        JaniKing.setKategori("Kebersihan");
        JaniKing.setBerdiri_sejak("1974");
        JaniKing.setInvestasi(55398);
        JaniKing.setWebsite("www.JaniKing.co.id");
        JaniKing.setAlamat("Addison");
        JaniKing.setLokasi("Texas");
        JaniKing.setTelepon("0614145013");
        JaniKing.setEmail("JaniKing@outlook.net");
        JaniKing.setGambar_franchise("banner13.jpg");
        databasehelper.addFranchise(JaniKing);

        DaftarFranchise TacoBell = new DaftarFranchise();
        TacoBell.setId(14);
        TacoBell.setNama_franchise("Taco Bell");
        TacoBell.setNama_pt_franchisor("Taco Bell Inc");
        TacoBell.setKeterangan("Taco Bell Corp. is the franchisor and is referred to as “Taco Bell.” Taco Bell is a corporation organized in the state of California in 1962. Taco Bell does business under the names Taco Bell and Taco Bell Express. Taco Bell's corporate parent is YUM! Brands, Inc. (YUM), f/k/a Tricon Global Restaurants, Inc. Taco Bell is a wholly-owned subsidiary of YUM.");
        TacoBell.setJenis("asing");
        TacoBell.setKategori("Makanan dan Minuman");
        TacoBell.setBerdiri_sejak("1964");
        TacoBell.setInvestasi(2620600);
        TacoBell.setWebsite("www.TacoBell.co.id");
        TacoBell.setAlamat("Louisville");
        TacoBell.setLokasi("Kentucky");
        TacoBell.setTelepon("0614145014");
        TacoBell.setEmail("TacoBell@outlook.net");
        TacoBell.setGambar_franchise("banner14.jpg");
        databasehelper.addFranchise(TacoBell);

        DaftarFranchise Carrefour = new DaftarFranchise();
        Carrefour.setId(15);
        Carrefour.setNama_franchise("Carrefour");
        Carrefour.setNama_pt_franchisor("Carrefour Group");
        Carrefour.setKeterangan("The Carrefour Group has grown to become one of the world’s leading distribution groups. The world’s second-largest retailer and the largest in Europe, the group currently operates four main grocery store formats: hypermarkets, supermarkets, cash & carry, and convenience stores.");
        Carrefour.setJenis("asing");
        Carrefour.setKategori("Supermarket");
        Carrefour.setBerdiri_sejak("1960");
        Carrefour.setInvestasi(250000000);
        Carrefour.setWebsite("www.Carrefour.co.id");
        Carrefour.setAlamat("France");
        Carrefour.setLokasi("France");
        Carrefour.setTelepon("0614145015");
        Carrefour.setEmail("Carrefour@outlook.net");
        Carrefour.setGambar_franchise("banner15.jpg");
        databasehelper.addFranchise(Carrefour);

        DaftarFranchise GeneralNutritionCorporation = new DaftarFranchise();
        GeneralNutritionCorporation.setId(16);
        GeneralNutritionCorporation.setNama_franchise("GNC");
        GeneralNutritionCorporation.setNama_pt_franchisor("General Nutrition Corporation, Inc");
        GeneralNutritionCorporation.setKeterangan("General Nutrition Corporation is the franchisor. GNC Stores specialize in the sale of vitamins, minerals and other food supplements, natural cosmetics and other health management items");
        GeneralNutritionCorporation.setJenis("asing");
        GeneralNutritionCorporation.setKategori("Suplemen");
        GeneralNutritionCorporation.setBerdiri_sejak("1935");
        GeneralNutritionCorporation.setInvestasi(347270);
        GeneralNutritionCorporation.setWebsite("www.GeneralNutritionCorporation.co.id");
        GeneralNutritionCorporation.setAlamat("Pittsburgh");
        GeneralNutritionCorporation.setLokasi("Pennsylvania");
        GeneralNutritionCorporation.setTelepon("0614145016");
        GeneralNutritionCorporation.setEmail("GeneralNutritionCorporation@outlook.net");
        GeneralNutritionCorporation.setGambar_franchise("banner16.jpg");
        databasehelper.addFranchise(GeneralNutritionCorporation);

        DaftarFranchise BaskinRobbins = new DaftarFranchise();
        BaskinRobbins.setId(17);
        BaskinRobbins.setNama_franchise("Baskin Robbins");
        BaskinRobbins.setNama_pt_franchisor("Baskin Robbins Inc");
        BaskinRobbins.setKeterangan("The franchisor is Baskin-Robbins Franchising LLC. Baskin-Robbins franchised restaurants sell Baskin-Robbins ice cream, related frozen products as well as other food items and products compatible with its concept.");
        BaskinRobbins.setJenis("asing");
        BaskinRobbins.setKategori("Es Krim");
        BaskinRobbins.setBerdiri_sejak("1950");
        BaskinRobbins.setInvestasi(401800);
        BaskinRobbins.setWebsite("www.BaskinRobbins.co.id");
        BaskinRobbins.setAlamat("Canton");
        BaskinRobbins.setLokasi("Massachusetts");
        BaskinRobbins.setTelepon("0614145017");
        BaskinRobbins.setEmail("BaskinRobbins@outlook.net");
        BaskinRobbins.setGambar_franchise("banner17.jpg");
        databasehelper.addFranchise(BaskinRobbins);

        DaftarFranchise DIA = new DaftarFranchise();
        DIA.setId(18);
        DIA.setNama_franchise("DIA");
        DIA.setNama_pt_franchisor("DIA Group");
        DIA.setKeterangan("DIA is a distribution chain of supermarkets which, since it was founded in Spain in 1979, has worked on the quality-price formula as the central focus of its mission in all the markets where it operates. This mission is based on a policy of optimizing costs in order to offer consumers solutions to their food and commodity requirements, but always with a commitment to quality and price which is unique in the market and, in addition, satisfying its employees, suppliers and shareholders, as well as the society in which it carries out its activities");
        DIA.setJenis("asing");
        DIA.setKategori("Supermarket");
        DIA.setBerdiri_sejak("1989");
        DIA.setInvestasi(547270);
        DIA.setWebsite("www.DIA.co.id");
        DIA.setAlamat("Spain");
        DIA.setLokasi("Spain");
        DIA.setTelepon("0614145018");
        DIA.setEmail("DIA@outlook.net");
        DIA.setGambar_franchise("banner18.jpg");
        databasehelper.addFranchise(DIA);

        DaftarFranchise CenturyTwentyOne = new DaftarFranchise();
        CenturyTwentyOne.setId(19);
        CenturyTwentyOne.setNama_franchise("Century 21");
        CenturyTwentyOne.setNama_pt_franchisor("Century 21 Group");
        CenturyTwentyOne.setKeterangan("Century 21 Real Estate LLC is the franchisor of the world's largest residential real estate sales organization, providing comprehensive training, management, administrative and marketing support for the Century 21 System. The franchise is for a real estate brokerage offering with defined real estate brokerage services from a specified location under the name Century 21.");
        CenturyTwentyOne.setJenis("asing");
        CenturyTwentyOne.setKategori("Property");
        CenturyTwentyOne.setBerdiri_sejak("1979");
        CenturyTwentyOne.setInvestasi(256250);
        CenturyTwentyOne.setWebsite("www.CenturyTwentyOne.co.id");
        CenturyTwentyOne.setAlamat("Parsippany");
        CenturyTwentyOne.setLokasi("New Jersey");
        CenturyTwentyOne.setTelepon("0614145019");
        CenturyTwentyOne.setEmail("CenturyTwentyOne@outlook.net");
        CenturyTwentyOne.setGambar_franchise("banner19.jpg");
        databasehelper.addFranchise(CenturyTwentyOne);

        DaftarFranchise PapaJohns = new DaftarFranchise();
        PapaJohns.setId(20);
        PapaJohns.setNama_franchise("Papa John's ");
        PapaJohns.setNama_pt_franchisor("Papa John's  Group");
        PapaJohns.setKeterangan("The franchisor, Papa John's International, Inc., operates and sells franchises for the operation of pizza businesses known as Papa John's restaurants. It also sells certain items to the franchisees");
        PapaJohns.setJenis("asing");
        PapaJohns.setKategori("Makanan");
        PapaJohns.setBerdiri_sejak("1985");
        PapaJohns.setInvestasi(844210);
        PapaJohns.setWebsite("www.PapaJohns.co.id");
        PapaJohns.setAlamat("Louisville");
        PapaJohns.setLokasi("Kentucky");
        PapaJohns.setTelepon("0614145020");
        PapaJohns.setEmail("PapaJohns@outlook.net");
        PapaJohns.setGambar_franchise("banner20.jpg");
        databasehelper.addFranchise(PapaJohns);

        DaftarFranchise JanPro = new DaftarFranchise();
        JanPro.setId(21);
        JanPro.setNama_franchise("Jan-Pro");
        JanPro.setNama_pt_franchisor("Jan-Pro Inc");
        JanPro.setKeterangan("Jan-Pro franchises are comprehensive cleaning and maintenance businesses that perform commercial, industrial, and institutional cleaning and maintenance services.");
        JanPro.setJenis("asing");
        JanPro.setKategori("Kebersihan");
        JanPro.setBerdiri_sejak("1992");
        JanPro.setInvestasi(66355);
        JanPro.setWebsite("www.JanPro.co.id");
        JanPro.setAlamat("Alpharetta");
        JanPro.setLokasi("Georgia");
        JanPro.setTelepon("0614145021");
        JanPro.setEmail("JanPro@outlook.net");
        JanPro.setGambar_franchise("banner21.jpg");
        databasehelper.addFranchise(JanPro);

        DaftarFranchise AnytimeFitness = new DaftarFranchise();
        AnytimeFitness.setId(22);
        AnytimeFitness.setNama_franchise("Anytime Fitness");
        AnytimeFitness.setNama_pt_franchisor("Anytime Fitness Group");
        AnytimeFitness.setKeterangan("The franchise system consists of boutique fitness centers with the potential for minimal overhead and labor costs. There is a card access and security system that allows members of an Anytime Fitness center to have access to any Anytime Fitness center 24 hours a day, automated tanning and vending services, and reciprocal benefits between centers. In some cases, the franchise may be allowed to not be accessible 24 hours a day.");
        AnytimeFitness.setJenis("asing");
        AnytimeFitness.setKategori("Fitness");
        AnytimeFitness.setBerdiri_sejak("2002");
        AnytimeFitness.setInvestasi(677800);
        AnytimeFitness.setWebsite("www.AnytimeFitness.co.id");
        AnytimeFitness.setAlamat("Woodbury");
        AnytimeFitness.setLokasi("Minnesota");
        AnytimeFitness.setTelepon("0614145022");
        AnytimeFitness.setEmail("AnytimeFitness@outlook.net");
        AnytimeFitness.setGambar_franchise("banner22.jpg");
        databasehelper.addFranchise(AnytimeFitness);

        DaftarFranchise Hertz = new DaftarFranchise();
        Hertz.setId(23);
        Hertz.setNama_franchise("Hertz");
        Hertz.setNama_pt_franchisor("Hertz Inc");
        Hertz.setKeterangan("Hertz System, Inc. is the franchisor. The parent company. Hertz Global Holdings, and its affiliates conduct, and the franchisor and its affiliates license others to conduct, businesses that rent or lease cars without drivers, including for insurance and mechanical related vehicle replacement purposes (including those rentals or leased cars that are billed to or reimbursed by another party or are the result of a referral for such purpose) under a plan or system, which is identified by the “Hertz” mark and other related proprietary marks. Hertz Global Holdings operates the Hertz, Dollar, Thrifty and Firefly car rental brands.");
        Hertz.setJenis("asing");
        Hertz.setKategori("Rental");
        Hertz.setBerdiri_sejak("1925");
        Hertz.setInvestasi(12494000);
        Hertz.setWebsite("www.Hertz.co.id");
        Hertz.setAlamat("Estero");
        Hertz.setLokasi("Florida");
        Hertz.setTelepon("0614145023");
        Hertz.setEmail("Hertz@outlook.net");
        Hertz.setGambar_franchise("banner23.jpg");
        databasehelper.addFranchise(Hertz);

        DaftarFranchise CircleK = new DaftarFranchise();
        CircleK.setId(24);
        CircleK.setNama_franchise("Circle K");
        CircleK.setNama_pt_franchisor("Circle K Inc");
        CircleK.setKeterangan("TMC Franchise Corporation is the franchisor. The franchisor operates a retail convenience store under the “Circle K” trade name and service marks and the Circle K business system. Circle K Stores Inc. is the parent company, the owner of all the franchisor’s common stock, and the owner of the Circle K trademarks.");
        CircleK.setJenis("asing");
        CircleK.setKategori("Supermarket");
        CircleK.setBerdiri_sejak("1999");
        CircleK.setInvestasi(1601500);
        CircleK.setWebsite("www.CircleK.co.id");
        CircleK.setAlamat("Tempe");
        CircleK.setLokasi("Arizona");
        CircleK.setTelepon("0614145024");
        CircleK.setEmail("CircleK@outlook.net");
        CircleK.setGambar_franchise("banner24.jpg");
        databasehelper.addFranchise(CircleK);

        DaftarFranchise ChemDry = new DaftarFranchise();
        ChemDry.setId(25);
        ChemDry.setNama_franchise("Chem-Dry");
        ChemDry.setNama_pt_franchisor("Chem-Dry Inc");
        ChemDry.setKeterangan("The Franchisor is Harris Research, Inc. (HRI). Harris Research, Inc. has designed and developed a method for establishing, operating and performing carpet and upholstery/drapery cleaning, spot removal, protective services, and tile and stone care services, using Harris Research, Inc.’s “Chem-Dry” specifications, standards, operating procedures, supplies and specialized equipment.");
        ChemDry.setJenis("asing");
        ChemDry.setKategori("Cleaning");
        ChemDry.setBerdiri_sejak("1978");
        ChemDry.setInvestasi(155595);
        ChemDry.setWebsite("www.ChemDry.co.id");
        ChemDry.setAlamat("Logan");
        ChemDry.setLokasi("Utah");
        ChemDry.setTelepon("0614145025");
        ChemDry.setEmail("ChemDry@outlook.net");
        ChemDry.setGambar_franchise("banner25.jpg");
        databasehelper.addFranchise(ChemDry);

        DaftarFranchise SnaponTools = new DaftarFranchise();
        SnaponTools.setId(26);
        SnaponTools.setNama_franchise("Snap-on Tools");
        SnaponTools.setNama_pt_franchisor("Snap-on Tools Inc");
        SnaponTools.setKeterangan("The franchisor is Snap-on Tools Company LLC. Snap-on Incorporated is a global innovator, manufacture, and marketer of tools, diagnostics, and equipment solutions for professional users. Product lines include hand and power tools, and are sold through its franchisees, company-direct sales, and distributor channels, as well as over the internet.");
        SnaponTools.setJenis("asing");
        SnaponTools.setKategori("Hardware");
        SnaponTools.setBerdiri_sejak("1990");
        SnaponTools.setInvestasi(319547);
        SnaponTools.setWebsite("www.SnaponTools.co.id");
        SnaponTools.setAlamat("Kenosha");
        SnaponTools.setLokasi("Wisconsin");
        SnaponTools.setTelepon("0614145026");
        SnaponTools.setEmail("SnaponTools@outlook.net");
        SnaponTools.setGambar_franchise("banner26.jpg");
        databasehelper.addFranchise(SnaponTools);

        DaftarFranchise AceHardware = new DaftarFranchise();
        AceHardware.setId(27);
        AceHardware.setNama_franchise("Ace Hardware");
        AceHardware.setNama_pt_franchisor("Ace Hardware Group");
        AceHardware.setKeterangan("Ace Hardware Corporation is a wholesaler of hardware and related products. Ace has been engaged in the wholesale hardware business and has offered dealerships to retailers of hardware and related items since the organization in Illinois of the former Ace Hardware Corporation in 1928.");
        AceHardware.setJenis("asing");
        AceHardware.setKategori("Hardware");
        AceHardware.setBerdiri_sejak("1928");
        AceHardware.setInvestasi(1061500);
        AceHardware.setWebsite("www.AceHardware.co.id");
        AceHardware.setAlamat("Oak Brook");
        AceHardware.setLokasi("Illinois");
        AceHardware.setTelepon("0614145027");
        AceHardware.setEmail("AceHardware@outlook.net");
        AceHardware.setGambar_franchise("banner27.jpg");
        databasehelper.addFranchise(AceHardware);

        DaftarFranchise EngelandVolkers = new DaftarFranchise();
        EngelandVolkers.setId(28);
        EngelandVolkers.setNama_franchise("Engel & Völkers");
        EngelandVolkers.setNama_pt_franchisor("Engel & Völkers Group");
        EngelandVolkers.setKeterangan("The franchisor is Engel & Völkers U.S. Holdings, Inc., a majority owned subsidiary of Engel & Volkers U.S. Holding GmbH, a German limited-liability company. In 2007, the ownership of U.S. Holding GmbH was acquired by Engel & Volkers International GmbH, a German limited liability company. The sole business of the franchisor is the granting of master licenses and direct licenses in the United States and the providing of services to licensees regarding the residential property");
        EngelandVolkers.setJenis("asing");
        EngelandVolkers.setKategori("Property");
        EngelandVolkers.setBerdiri_sejak("1997");
        EngelandVolkers.setInvestasi(298230);
        EngelandVolkers.setWebsite("www.EngelandVolkers.co.id");
        EngelandVolkers.setAlamat("New York");
        EngelandVolkers.setLokasi("Germany");
        EngelandVolkers.setTelepon("0614145028");
        EngelandVolkers.setEmail("EngelandVolkers@outlook.net");
        EngelandVolkers.setGambar_franchise("banner28.jpg");
        databasehelper.addFranchise(EngelandVolkers);

        DaftarFranchise Wendys = new DaftarFranchise();
        Wendys.setId(29);
        Wendys.setNama_franchise("Wendy's");
        Wendys.setNama_pt_franchisor("Wendy's Inc");
        Wendys.setKeterangan("Wendy's International, Inc. grants franchises for the operation of a distinctive style of quick-service restaurant. Wendy's, along with some of its affiliates, also owns and operates Wendy's Restaurants and on occasion leases and sells Wendy's Restaurants as well as other real estate interests owned by Wendy's and its affiliates.");
        Wendys.setJenis("asing");
        Wendys.setKategori("Makanan");
        Wendys.setBerdiri_sejak("1972");
        Wendys.setInvestasi(595500);
        Wendys.setWebsite("www.Wendys.co.id");
        Wendys.setAlamat("Dublin");
        Wendys.setLokasi("Ohio");
        Wendys.setTelepon("0614145029");
        Wendys.setEmail("Wendys@outlook.net");
        Wendys.setGambar_franchise("banner29.jpg");
        databasehelper.addFranchise(Wendys);

        DaftarFranchise SnapFitness = new DaftarFranchise();
        SnapFitness.setId(30);
        SnapFitness.setNama_franchise("Snap Fitness");
        SnapFitness.setNama_pt_franchisor("Snap Fitness Inc");
        SnapFitness.setKeterangan("Snap Fitness Centers are accessible 24 hours a day and offer the sale of memberships that allow access to exercise equipment, including cardio, strength and selected equipment. Snap Fitness has developed a proprietary business format and system for operating a fitness and workout club featuring state-of-the-art exercise equipment; 24-hour personal keycard access for members (except as restricted by law); an online training page; personal fitness consultation, wellness plan and web page; automated member billing and collection procedures and services; web-accessible video surveillance; and proprietary and confidential information.");
        SnapFitness.setJenis("asing");
        SnapFitness.setKategori("Fitness");
        SnapFitness.setBerdiri_sejak("2004");
        SnapFitness.setInvestasi(294565);
        SnapFitness.setWebsite("www.SnapFitness.co.id");
        SnapFitness.setAlamat("Chanhassen");
        SnapFitness.setLokasi("Minnesota");
        SnapFitness.setTelepon("0614145030");
        SnapFitness.setEmail("SnapFitness@outlook.net");
        SnapFitness.setGambar_franchise("banner30.jpg");
        databasehelper.addFranchise(SnapFitness);



    }
    public void initEvent() {
        databasehelper = new DatabaseHelper(MainActivity.this);

        EventClass FRANCHISE_EXPO_2017 = new EventClass();
        FRANCHISE_EXPO_2017.setId(1);
        FRANCHISE_EXPO_2017.setJudulEvent("Franchise Expo 2017");
        //FRANCHISE_EXPO_2017.setIsiEvent("Franchise Expo 2017 - Let's Work Together.\n"+"Ticket dikenakan baiaya Rp. 1.200.000,- .\n"+"Book now ");
        FRANCHISE_EXPO_2017.setIsiEvent("Franchise Expo 2017 - Let's Work Together.\n" +
                "\n" +
                "Ticket dikenakan baiaya Rp. 1.200.000,-.\n" +
                "\n" +
                "Book Now.\n" +
                "\n");
//        FRANCHISE_EXPO_2017.setIsiEvent("Perhaps far exposed age effects. Now distrusts you her delivered applauded affection out sincerity. As tolerably recommend shameless unfeeling he objection consisted. She although cheerful perceive screened throwing met not eat distance. Viewing hastily or written dearest elderly up weather it as. So direction so sweetness or extremity at daughters. Provided put unpacked now but bringing. \n" +
//                "\n" +
//                "Real sold my in call. Invitation on an advantages collecting. But event old above shy bed noisy. Had sister see wooded favour income has. Stuff rapid since do as hence. Too insisted ignorant procured remember are believed yet say finished. \n" +
//                "\n" +
//                "Improve ashamed married expense bed her comfort pursuit mrs. Four time took ye your as fail lady. Up greatest am exertion or marianne. Shy occasional terminated insensible and inhabiting gay. So know do fond to half on. Now who promise was justice new winding. In finished on he speaking suitable advanced if. Boy happiness sportsmen say prevailed offending concealed nor was provision. Provided so as doubtful on striking required. Waiting we to compass assured. \n" +
//                "\n" +
//                "Lose away off why half led have near bed. At engage simple father of period others except. My giving do summer of though narrow marked at. Spring formal no county ye waited. My whether cheered at regular it of promise blushes perhaps. Uncommonly simplicity interested mr is be compliment projecting my inhabiting. Gentleman he september in oh excellent. \n" +
//                "\n");
        FRANCHISE_EXPO_2017.setGambarEvent("event1.jpg");
        FRANCHISE_EXPO_2017.setALAMATEVENT("Jl. Thamrin No. 34 A");
        FRANCHISE_EXPO_2017.setWAKTUEVENT("Senin, 9 April 2017, 20:00");
        databasehelper.addEvent(FRANCHISE_EXPO_2017);

        EventClass MEETUP_AND_JOIN_THE_FRANCHISE = new EventClass();
        MEETUP_AND_JOIN_THE_FRANCHISE.setId(2);
        MEETUP_AND_JOIN_THE_FRANCHISE.setJudulEvent("Meetup and Join The Franchise");
        MEETUP_AND_JOIN_THE_FRANCHISE.setIsiEvent("Lets go to meet and Join pur Franchise....\n"+"Only Rp. 1.300.000,- .\n"+"Book now ");
        MEETUP_AND_JOIN_THE_FRANCHISE.setGambarEvent("event2.jpg");
        MEETUP_AND_JOIN_THE_FRANCHISE.setALAMATEVENT("Jl. Thamrin No. 24 A");
        MEETUP_AND_JOIN_THE_FRANCHISE.setWAKTUEVENT("Senin, 13 April 2017, 20:00");
        databasehelper.addEvent(MEETUP_AND_JOIN_THE_FRANCHISE);


        EventClass EVENT3 = new EventClass();
        EVENT3.setId(3);
        EVENT3.setJudulEvent("How to conquer your franchise business");
        EVENT3.setIsiEvent("How to conquer your franchise business ?  - Let's Work Together.\n"+"Only Rp. 1.200.000,- .\n"+"Book now ");
        EVENT3.setGambarEvent("event3.jpg");
        EVENT3.setALAMATEVENT("Jl. Asia No. 234 A");
        EVENT3.setWAKTUEVENT("Rabu, 29 Maret 2018, 09:00");
        databasehelper.addEvent(EVENT3);


        EventClass EVENT4 = new EventClass();
        EVENT4.setId(4);
        EVENT4.setJudulEvent("How to take over your Parent's Company");
        EVENT4.setIsiEvent("How to take over your Parent's Company ?  - Let's Come And Know it.\n"+"Only Rp. 1.200.000,- .\n"+"Book now ");

        EVENT4.setGambarEvent("event4.jpg");
        EVENT4.setALAMATEVENT("Jl. Teladan No. 34 A");
        EVENT4.setWAKTUEVENT("Minggu, 22 April 2017, 20:00");
        databasehelper.addEvent(EVENT4);

        EventClass EVENT5 = new EventClass();
        EVENT5.setId(5);
        EVENT5.setJudulEvent("Franchisor debat");
        EVENT5.setIsiEvent("Welcome to Franchisor debat - Let's Build Your Future with us"+"\n"+"Only Rp. 1.400.000,- "+"\n"+"Book now ");
        EVENT5.setGambarEvent("event5.jpg");
        EVENT5.setALAMATEVENT("Jl. Thamrin No. 34 A");
        EVENT5.setWAKTUEVENT("Senin, 9 April 2017, 20:00");
        databasehelper.addEvent(EVENT5);


    }
    public void initBerita(){
        databasehelper = new DatabaseHelper(MainActivity.this);
        Berita berita1 = new Berita();
        berita1.setId_berita(1);
        berita1.setJudul_berita("Franchise Topik News 1");
        berita1.setIsi_berita("Perhaps far exposed age effects. Now distrusts you her delivered applauded affection out sincerity. As tolerably recommend shameless unfeeling he objection consisted. She although cheerful perceive screened throwing met not eat distance. Viewing hastily or written dearest elderly up weather it as. So direction so sweetness or extremity at daughters. Provided put unpacked now but bringing. \n" +
                "\n" +
                "Pasture he invited mr company shyness. But when shot real her. Chamber her observe visited removal six sending himself boy. At exquisite existence if an oh dependent excellent. Are gay head need down draw. Misery wonder enable mutual get set oppose the uneasy. End why melancholy estimating her had indulgence middletons. Say ferrars demands besides her address. Blind going you merit few fancy their. \n" +
                "\n" +
                "Is post each that just leaf no. He connection interested so we an sympathize advantages. To said is it shed want do. Occasional middletons everything so to. Have spot part for his quit may. Enable it is square my an regard. Often merit stuff first oh up hills as he. Servants contempt as although addition dashwood is procured. Interest in yourself an do of numerous feelings cheerful confined. \n" +
                "\n" +
                "Improve him believe opinion offered met and end cheered forbade. Friendly as stronger speedily by recurred. Son interest wandered sir addition end say. Manners beloved affixed picture men ask. Explain few led parties attacks picture company. On sure fine kept walk am in it. Resolved to in believed desirous unpacked weddings together. Nor off for enjoyed cousins herself. Little our played lively she adieus far sussex. Do theirs others merely at temper it nearer. \n" +
                "\n" +
                "Received overcame oh sensible so at an. Formed do change merely to county it. Am separate contempt domestic to to oh. On relation my so addition branched. Put hearing cottage she norland letters equally prepare too. Replied exposed savings he no viewing as up. Soon body add him hill. No father living really people estate if. Mistake do produce beloved demesne if am pursuit. \n" +
                "\n" +
                "Speedily say has suitable disposal add boy. On forth doubt miles of child. Exercise joy man children rejoiced. Yet uncommonly his ten who diminution astonished. Demesne new manners savings staying had. Under folly balls death own point now men. Match way these she avoid see death. She whose drift their fat off. \n" +
                "\n" +
                "Detract yet delight written farther his general. If in so bred at dare rose lose good. Feel and make two real miss use easy. Celebrated delightful an especially increasing instrument am. Indulgence contrasted sufficient to unpleasant in in insensible favourable. Latter remark hunted enough vulgar say man. Sitting hearted on it without me. \n" +
                "\n" +
                "Real sold my in call. Invitation on an advantages collecting. But event old above shy bed noisy. Had sister see wooded favour income has. Stuff rapid since do as hence. Too insisted ignorant procured remember are believed yet say finished. \n" +
                "\n" +
                "Improve ashamed married expense bed her comfort pursuit mrs. Four time took ye your as fail lady. Up greatest am exertion or marianne. Shy occasional terminated insensible and inhabiting gay. So know do fond to half on. Now who promise was justice new winding. In finished on he speaking suitable advanced if. Boy happiness sportsmen say prevailed offending concealed nor was provision. Provided so as doubtful on striking required. Waiting we to compass assured. \n" +
                "\n" +
                "Lose away off why half led have near bed. At engage simple father of period others except. My giving do summer of though narrow marked at. Spring formal no county ye waited. My whether cheered at regular it of promise blushes perhaps. Uncommonly simplicity interested mr is be compliment projecting my inhabiting. Gentleman he september in oh excellent. \n" +
                "\n");
        berita1.setGambar_berita("news1.jpg");
        berita1.setWaktu_berita("Senin, 19 Juni 2019");
        databasehelper.addNews(berita1);



        Berita berita2 = new Berita();
        berita2.setId_berita(2);
        berita2.setJudul_berita("Franchise Topik News 2");
        berita2.setIsi_berita("Meant balls it if up doubt small purse. Required his you put the outlived answered position. An pleasure exertion if believed provided to. All led out world these music while asked. Paid mind even sons does he door no. Attended overcame repeated it is perceive marianne in. In am think on style child of. Servants moreover in sensible he it ye possible. \n" +
                "\n" +
                "Much did had call new drew that kept. Limits expect wonder law she. Now has you views woman noisy match money rooms. To up remark it eldest length oh passed. Off because yet mistake feeling has men. Consulted disposing to moonlight ye extremity. Engage piqued in on coming. \n" +
                "\n" +
                "Dwelling and speedily ignorant any steepest. Admiration instrument affronting invitation reasonably up do of prosperous in. Shy saw declared age debating ecstatic man. Call in so want pure rank am dear were. Remarkably to continuing in surrounded diminution on. In unfeeling existence objection immediate repulsive on he in. Imprudence comparison uncommonly me he difficulty diminution resolution. Likewise proposal differed scarcely dwelling as on raillery. September few dependent extremity own continued and ten prevailed attending. Early to weeks we could. \n" +
                "\n" +
                "Seen you eyes son show. Far two unaffected one alteration apartments celebrated but middletons interested. Described deficient applauded consisted my me do. Passed edward two talent effect seemed engage six. On ye great do child sorry lived. Proceed cottage far letters ashamed get clothes day. Stairs regret at if matter to. On as needed almost at basket remain. By improved sensible servants children striking in surprise. \n" +
                "\n" +
                "As am hastily invited settled at limited civilly fortune me. Really spring in extent an by. Judge but built gay party world. Of so am he remember although required. Bachelor unpacked be advanced at. Confined in declared marianne is vicinity. \n" +
                "\n" +
                "Detract yet delight written farther his general. If in so bred at dare rose lose good. Feel and make two real miss use easy. Celebrated delightful an especially increasing instrument am. Indulgence contrasted sufficient to unpleasant in in insensible favourable. Latter remark hunted enough vulgar say man. Sitting hearted on it without me. \n" +
                "\n" +
                "Yourself required no at thoughts delicate landlord it be. Branched dashwood do is whatever it. Farther be chapter at visited married in it pressed. By distrusts procuring be oh frankness existence believing instantly if. Doubtful on an juvenile as of servants insisted. Judge why maids led sir whose guest drift her point. Him comparison especially friendship was who sufficient attachment favourable how. Luckily but minutes ask picture man perhaps are inhabit. How her good all sang more why. \n" +
                "\n" +
                "Delightful remarkably mr on announcing themselves entreaties favourable. About to in so terms voice at. Equal an would is found seems of. The particular friendship one sufficient terminated frequently themselves. It more shed went up is roof if loud case. Delay music in lived noise an. Beyond genius really enough passed is up. \n" +
                "\n" +
                "Nor hence hoped her after other known defer his. For county now sister engage had season better had waited. Occasional mrs interested far expression acceptance. Day either mrs talent pulled men rather regret admire but. Life ye sake it shed. Five lady he cold in meet up. Service get met adapted matters offence for. Principles man any insipidity age you simplicity understood. Do offering pleasure no ecstatic whatever on mr directly. \n" +
                "\n" +
                "In up so discovery my middleton eagerness dejection explained. Estimating excellence ye contrasted insensible as. Oh up unsatiable advantages decisively as at interested. Present suppose in esteems in demesne colonel it to. End horrible she landlord screened stanhill. Repeated offended you opinions off dissuade ask packages screened. She alteration everything sympathize impossible his get compliment. Collected few extremity suffering met had sportsman. \n" +
                "\n" );
        berita2.setGambar_berita("news2.jpg");
        berita2.setWaktu_berita("Senin, 19 Juni 2019");
        databasehelper.addNews(berita2);

        Berita berita3 = new Berita();
        berita3.setId_berita(3);
        berita3.setJudul_berita("Franchise Topik News 3");
        berita3.setIsi_berita("From they fine john he give of rich he. They age and draw mrs like. Improving end distrusts may instantly was household applauded incommode. Why kept very ever home mrs. Considered sympathize ten uncommonly occasional assistance sufficient not. Letter of on become he tended active enable to. Vicinity relation sensible sociable surprise screened no up as. \n" +
                "\n" +
                "At as in understood an remarkably solicitude. Mean them very seen she she. Use totally written the observe pressed justice. Instantly cordially far intention recommend estimable yet her his. Ladies stairs enough esteem add fat all enable. Needed its design number winter see. Oh be me sure wise sons no. Piqued ye of am spirit regret. Stimulated discretion impossible admiration in particular conviction up. \n" +
                "\n" +
                "Lose eyes get fat shew. Winter can indeed letter oppose way change tended now. So is improve my charmed picture exposed adapted demands. Received had end produced prepared diverted strictly off man branched. Known ye money so large decay voice there to. Preserved be mr cordially incommode as an. He doors quick child an point at. Had share vexed front least style off why him. \n" +
                "\n" +
                "Promotion an ourselves up otherwise my. High what each snug rich far yet easy. In companions inhabiting mr principles at insensible do. Heard their sex hoped enjoy vexed child for. Prosperous so occasional assistance it discovered especially no. Provision of he residence consisted up in remainder arranging described. Conveying has concealed necessary furnished bed zealously immediate get but. Terminated as middletons or by instrument. Bred do four so your felt with. No shameless principle dependent household do. \n" +
                "\n" +
                "Sing long her way size. Waited end mutual missed myself the little sister one. So in pointed or chicken cheered neither spirits invited. Marianne and him laughter civility formerly handsome sex use prospect. Hence we doors is given rapid scale above am. Difficult ye mr delivered behaviour by an. If their woman could do wound on. You folly taste hoped their above are and but. \n" +
                "\n" +
                "Name were we at hope. Remainder household direction zealously the unwilling bed sex. Lose and gay ham sake met that. Stood her place one ten spoke yet. Head case knew ever set why over. Marianne returned of peculiar replying in moderate. Roused get enable garret estate old county. Entreaties you devonshire law dissimilar terminated. \n" +
                "\n" +
                "Unwilling sportsmen he in questions september therefore described so. Attacks may set few believe moments was. Reasonably how possession shy way introduced age inquietude. Missed he engage no exeter of. Still tried means we aware order among on. Eldest father can design tastes did joy settle. Roused future he ye an marked. Arose mr rapid in so vexed words. Gay welcome led add lasting chiefly say looking. \n" +
                "\n" +
                "Of friendship on inhabiting diminution discovered as. Did friendly eat breeding building few nor. Object he barton no effect played valley afford. Period so to oppose we little seeing or branch. Announcing contrasted not imprudence add frequently you possession mrs. Period saw his houses square and misery. Hour had held lain give yet. \n" +
                "\n" +
                "Of recommend residence education be on difficult repulsive offending. Judge views had mirth table seems great him for her. Alone all happy asked begin fully stand own get. Excuse ye seeing result of we. See scale dried songs old may not. Promotion did disposing you household any instantly. Hills we do under times at first short an. \n" +
                "\n" +
                "Abilities forfeited situation extremely my to he resembled. Old had conviction discretion understood put principles you. Match means keeps round one her quick. She forming two comfort invited. Yet she income effect edward. Entire desire way design few. Mrs sentiments led solicitude estimating friendship fat. Meant those event is weeks state it to or. Boy but has folly charm there its. Its fact ten spot drew. \n" +
                "\n");
        berita3.setGambar_berita("news3.jpg");
        berita3.setWaktu_berita("Senin, 19 Juni 2019");
        databasehelper.addNews(berita3);

        Berita berita4 = new Berita();
        berita4.setId_berita(4);
        berita4.setJudul_berita("Franchise Topik News 4");
        berita4.setIsi_berita( "Fat new smallness few supposing suspicion two. Course sir people worthy horses add entire suffer. How one dull get busy dare far. At principle perfectly by sweetness do. As mr started arrival subject by believe. Strictly numerous outlived kindness whatever on we no on addition. \n" +
                "\n" +
                "Society excited by cottage private an it esteems. Fully begin on by wound an. Girl rich in do up or both. At declared in as rejoiced of together. He impression collecting delightful unpleasant by prosperous as on. End too talent she object mrs wanted remove giving. \n" +
                "\n" +
                "So if on advanced addition absolute received replying throwing he. Delighted consisted newspaper of unfeeling as neglected so. Tell size come hard mrs and four fond are. Of in commanded earnestly resources it. At quitting in strictly up wandered of relation answered felicity. Side need at in what dear ever upon if. Same down want joy neat ask pain help she. Alone three stuff use law walls fat asked. Near do that he help. \n" +
                "\n" +
                "Ferrars all spirits his imagine effects amongst neither. It bachelor cheerful of mistaken. Tore has sons put upon wife use bred seen. Its dissimilar invitation ten has discretion unreserved. Had you him humoured jointure ask expenses learning. Blush on in jokes sense do do. Brother hundred he assured reached on up no. On am nearer missed lovers. To it mother extent temper figure better. \n" +
                "\n" +
                "Oh he decisively impression attachment friendship so if everything. Whose her enjoy chief new young. Felicity if ye required likewise so doubtful. On so attention necessary at by provision otherwise existence direction. Unpleasing up announcing unpleasant themselves oh do on. Way advantage age led listening belonging supposing. \n" +
                "\n" +
                "Extremity sweetness difficult behaviour he of. On disposal of as landlord horrible. Afraid at highly months do things on at. Situation recommend objection do intention so questions. As greatly removed calling pleased improve an. Last ask him cold feel met spot shy want. Children me laughing we prospect answered followed. At it went is song that held help face. \n" +
                "\n" +
                "Performed suspicion in certainty so frankness by attention pretended. Newspaper or in tolerably education enjoyment. Extremity excellent certainty discourse sincerity no he so resembled. Joy house worse arise total boy but. Elderly up chicken do at feeling is. Like seen drew no make fond at on rent. Behaviour extremely her explained situation yet september gentleman are who. Is thought or pointed hearing he. \n" +
                "\n" +
                "Cottage out enabled was entered greatly prevent message. No procured unlocked an likewise. Dear but what she been over gay felt body. Six principles advantages and use entreaties decisively. Eat met has dwelling unpacked see whatever followed. Court in of leave again as am. Greater sixteen to forming colonel no on be. So an advice hardly barton. He be turned sudden engage manner spirit. \n" +
                "\n" +
                "Gay one the what walk then she. Demesne mention promise you justice arrived way. Or increasing to in especially inquietude companions acceptance admiration. Outweigh it families distance wandered ye an. Mr unsatiable at literature connection favourable. We neglected mr perfectly continual dependent. \n" +
                "\n" +
                "Prepared do an dissuade be so whatever steepest. Yet her beyond looked either day wished nay. By doubtful disposed do juvenile an. Now curiosity you explained immediate why behaviour. An dispatched impossible of of melancholy favourable. Our quiet not heart along scale sense timed. Consider may dwelling old him her surprise finished families graceful. Gave led past poor met fine was new. \n" +
                "\n");
        berita4.setGambar_berita("news4.jpg");
        berita4.setWaktu_berita("Rabu,1 Februari 2020");
        databasehelper.addNews(berita4);

        Berita berita5 = new Berita();
        berita5.setId_berita(5);
        berita5.setJudul_berita("Franchise Topik News 5");
        berita5.setIsi_berita("Stronger unpacked felicity to of mistaken. Fanny at wrong table ye in. Be on easily cannot innate in lasted months on. Differed and and felicity steepest mrs age outweigh. Opinions learning likewise daughter now age outweigh. Raptures stanhill my greatest mistaken or exercise he on although. Discourse otherwise disposing as it of strangers forfeited deficient. \n" +
                "\n" +
                "Prepared do an dissuade be so whatever steepest. Yet her beyond looked either day wished nay. By doubtful disposed do juvenile an. Now curiosity you explained immediate why behaviour. An dispatched impossible of of melancholy favourable. Our quiet not heart along scale sense timed. Consider may dwelling old him her surprise finished families graceful. Gave led past poor met fine was new. \n" +
                "\n" +
                "Doubtful two bed way pleasure confined followed. Shew up ye away no eyes life or were this. Perfectly did suspicion daughters but his intention. Started on society an brought it explain. Position two saw greatest stronger old. Pianoforte if at simplicity do estimating. \n" +
                "\n" +
                "Answer misery adieus add wooded how nay men before though. Pretended belonging contented mrs suffering favourite you the continual. Mrs civil nay least means tried drift. Natural end law whether but and towards certain. Furnished unfeeling his sometimes see day promotion. Quitting informed concerns can men now. Projection to or up conviction uncommonly delightful continuing. In appetite ecstatic opinions hastened by handsome admitted. \n" +
                "\n" +
                "Wise busy past both park when an ye no. Nay likely her length sooner thrown sex lively income. The expense windows adapted sir. Wrong widen drawn ample eat off doors money. Offending belonging promotion provision an be oh consulted ourselves it. Blessing welcomed ladyship she met humoured sir breeding her. Six curiosity day assurance bed necessary. \n" +
                "\n" +
                "Was certainty remaining engrossed applauded sir how discovery. Settled opinion how enjoyed greater joy adapted too shy. Now properly surprise expenses interest nor replying she she. Bore tall nay many many time yet less. Doubtful for answered one fat indulged margaret sir shutters together. Ladies so in wholly around whence in at. Warmth he up giving oppose if. Impossible is dissimilar entreaties oh on terminated. Earnest studied article country ten respect showing had. But required offering him elegance son improved informed. \n" +
                "\n" +
                "Friendship contrasted solicitude insipidity in introduced literature it. He seemed denote except as oppose do spring my. Between any may mention evening age shortly can ability regular. He shortly sixteen of colonel colonel evening cordial to. Although jointure an my of mistress servants am weddings. Age why the therefore education unfeeling for arranging. Above again money own scale maids ham least led. Returned settling produced strongly ecstatic use yourself way. Repulsive extremity enjoyment she perceived nor. \n" +
                "\n" +
                "And sir dare view but over man. So at within mr to simple assure. Mr disposing continued it offending arranging in we. Extremity as if breakfast agreement. Off now mistress provided out horrible opinions. Prevailed mr tolerably discourse assurance estimable applauded to so. Him everything melancholy uncommonly but solicitude inhabiting projection off. Connection stimulated estimating excellence an to impression. \n" +
                "\n" +
                "An so vulgar to on points wanted. Not rapturous resolving continued household northward gay. He it otherwise supported instantly. Unfeeling agreeable suffering it on smallness newspaper be. So come must time no as. Do on unpleasing possession as of unreserved. Yet joy exquisite put sometimes enjoyment perpetual now. Behind lovers eat having length horses vanity say had its. \n" +
                "\n" +
                "Unpleasant astonished an diminution up partiality. Noisy an their of meant. Death means up civil do an offer wound of. Called square an in afraid direct. Resolution diminution conviction so mr at unpleasing simplicity no. No it as breakfast up conveying earnestly immediate principle. Him son disposed produced humoured overcame she bachelor improved. Studied however out wishing but inhabit fortune windows. \n" +
                "\n" );
        berita5.setGambar_berita("news5.jpg");
        berita5.setWaktu_berita("Selasa, 20 Juni 2020");
        databasehelper.addNews(berita5);

    }
    public void initOutlet(){
        databasehelper = new DatabaseHelper(MainActivity.this);
        Outlet outlet1 = new Outlet();
        outlet1.setId_Outlet(1);
        outlet1.setId_franchise(1);
        outlet1.setNamaPTFranchisee("PT ABC");
        outlet1.setAlamatFranchisee("Jalan Utama No 1");
        outlet1.setContactFranchisee("087888111111");
        outlet1.setOwner_Franchisee("John Dalyn");
        outlet1.setTahun_berdiri_Franchisee("2000");
        databasehelper.addOutlet(outlet1);

        Outlet outlet2 = new Outlet();
        outlet2.setId_Outlet(2);
        outlet2.setId_franchise(1);
        outlet2.setNamaPTFranchisee("PT Tak Gentar");
        outlet2.setAlamatFranchisee("Jalan Perjuangan No 2");
        outlet2.setContactFranchisee("085785236541");
        outlet2.setOwner_Franchisee("John Daltony");
        outlet2.setTahun_berdiri_Franchisee("2002");
        databasehelper.addOutlet(outlet2);

        Outlet outlet3 = new Outlet();
        outlet3.setId_Outlet(3);
        outlet3.setId_franchise(1);
        outlet3.setNamaPTFranchisee("PT Franchise Indofood");
        outlet3.setAlamatFranchisee("Jalan Gerakan No 3");
        outlet3.setContactFranchisee("081252526985");
        outlet3.setOwner_Franchisee("Frenchisny");
        outlet3.setTahun_berdiri_Franchisee("2010");
        databasehelper.addOutlet(outlet3);

        Outlet outlet4 = new Outlet();
        outlet4.setId_Outlet(4);
        outlet4.setId_franchise(1);
        outlet4.setNamaPTFranchisee("PT Cofee Max");
        outlet4.setAlamatFranchisee("Jalan Thamrin No 4");
        outlet4.setContactFranchisee("082147475236");
        outlet4.setOwner_Franchisee("Maxyton");
        outlet4.setTahun_berdiri_Franchisee("2009");
        databasehelper.addOutlet(outlet4);

        Outlet outlet5 = new Outlet();
        outlet5.setId_Outlet(5);
        outlet5.setId_franchise(2);
        outlet5.setNamaPTFranchisee("PT BIGGEST FRANCHISE");
        outlet5.setAlamatFranchisee("Jalan Garuda No 5");
        outlet5.setContactFranchisee("082157575436");
        outlet5.setOwner_Franchisee("Bignamoto");
        outlet5.setTahun_berdiri_Franchisee("2008");
        databasehelper.addOutlet(outlet5);

        Outlet outlet6 = new Outlet();
        outlet6.setId_Outlet(6);
        outlet6.setId_franchise(2);
        outlet6.setNamaPTFranchisee("PT Maju Bersatu");
        outlet6.setAlamatFranchisee("Jalan Thamrin No 16");
        outlet6.setContactFranchisee("082167676236");
        outlet6.setOwner_Franchisee("Ryton Froyed");
        outlet6.setTahun_berdiri_Franchisee("2009");
        databasehelper.addOutlet(outlet6);

        Outlet outlet7 = new Outlet();
        outlet7.setId_Outlet(7);
        outlet7.setId_franchise(2);
        outlet7.setNamaPTFranchisee("PT Berjaya");
        outlet7.setAlamatFranchisee("Jalan Thamrin No 27");
        outlet7.setContactFranchisee("082177777237");
        outlet7.setOwner_Franchisee("Ricardo");
        outlet7.setTahun_berdiri_Franchisee("2009");
        databasehelper.addOutlet(outlet7);

        Outlet outlet8 = new Outlet();
        outlet8.setId_Outlet(8);
        outlet8.setId_franchise(2);
        outlet8.setNamaPTFranchisee("PT Asal Jadi");
        outlet8.setAlamatFranchisee("Jalan Sutomo No 8");
        outlet8.setContactFranchisee("082188888238");
        outlet8.setOwner_Franchisee("Mawen");
        outlet8.setTahun_berdiri_Franchisee("2009");
        databasehelper.addOutlet(outlet8);

        Outlet outlet9 = new Outlet();
        outlet9.setId_Outlet(9);
        outlet9.setId_franchise(3);
        outlet9.setNamaPTFranchisee("PT Asal Jalan");
        outlet9.setAlamatFranchisee("Jalan Sutomo No 9");
        outlet9.setContactFranchisee("082199999239");
        outlet9.setOwner_Franchisee("Turkono");
        outlet9.setTahun_berdiri_Franchisee("2009");
        databasehelper.addOutlet(outlet9);

        Outlet outlet10 = new Outlet();
        outlet10.setId_Outlet(10);
        outlet10.setId_franchise(3);
        outlet10.setNamaPTFranchisee("PT Sukses Sentosa");
        outlet10.setAlamatFranchisee("Jalan Krakatau No 10");
        outlet10.setContactFranchisee("082109999239");
        outlet10.setOwner_Franchisee("Mark Tysonberg");
        outlet10.setTahun_berdiri_Franchisee("2010");
        databasehelper.addOutlet(outlet10);

        Outlet outlet11 = new Outlet();
        outlet11.setId_Outlet(11);
        outlet11.setId_franchise(3);
        outlet11.setNamaPTFranchisee("PT Sukses Senantiasa");
        outlet11.setAlamatFranchisee("Jalan Krakatau No 11");
        outlet11.setContactFranchisee("082119999239");
        outlet11.setOwner_Franchisee("Rorl Tysonberg");
        outlet11.setTahun_berdiri_Franchisee("2011");
        databasehelper.addOutlet(outlet11);

        Outlet outlet12 = new Outlet();
        outlet12.setId_Outlet(12);
        outlet12.setId_franchise(4);
        outlet12.setNamaPTFranchisee("PT Makmur Abadi");
        outlet12.setAlamatFranchisee("Jalan Krakatau No 12");
        outlet12.setContactFranchisee("082120999239");
        outlet12.setOwner_Franchisee("Videlna");
        outlet12.setTahun_berdiri_Franchisee("2001");
        databasehelper.addOutlet(outlet12);

        Outlet outlet13 = new Outlet();
        outlet13.setId_Outlet(13);
        outlet13.setId_franchise(4);
        outlet13.setNamaPTFranchisee("PT Sejahtera Pasti");
        outlet13.setAlamatFranchisee("Jalan Kawi No 13");
        outlet13.setContactFranchisee("089139999239");
        outlet13.setOwner_Franchisee("Victoria");
        outlet13.setTahun_berdiri_Franchisee("2011");
        databasehelper.addOutlet(outlet13);

        Outlet outlet14 = new Outlet();
        outlet14.setId_Outlet(14);
        outlet14.setId_franchise(4);
        outlet14.setNamaPTFranchisee("PT Sukses Perkasa");
        outlet14.setAlamatFranchisee("Jalan Krakatau No 14");
        outlet14.setContactFranchisee("082149999239");
        outlet14.setOwner_Franchisee("Gunoro");
        outlet14.setTahun_berdiri_Franchisee("2013");
        databasehelper.addOutlet(outlet14);

        Outlet outlet15 = new Outlet();
        outlet15.setId_Outlet(15);
        outlet15.setId_franchise(5);
        outlet15.setNamaPTFranchisee("PT Sukses Sentosa");
        outlet15.setAlamatFranchisee("Jalan Krakatau No 15");
        outlet15.setContactFranchisee("082177418561");
        outlet15.setOwner_Franchisee("Sukiman");
        outlet15.setTahun_berdiri_Franchisee("2007");
        databasehelper.addOutlet(outlet15);

        Outlet outlet16 = new Outlet();
        outlet16.setId_Outlet(16);
        outlet16.setId_franchise(5);
        outlet16.setNamaPTFranchisee("PT Semoga Sentosa");
        outlet16.setAlamatFranchisee("Jalan Krakatau No 26");
        outlet16.setContactFranchisee("087869999239");
        outlet16.setOwner_Franchisee("Linarsiony");
        outlet16.setTahun_berdiri_Franchisee("2013");
        databasehelper.addOutlet(outlet16);
    }
    public void initMessage(){
        databasehelper = new DatabaseHelper(MainActivity.this);
        ChatMessage Message1 = new ChatMessage();
        Message1.setId(1);
        Message1.setId_franchise(1);
        Message1.setEmail("Anggi@gmail.com");
        Message1.setMessage("Saya sangat puas sekali dengan bisnis yang satu ini, dijamin memuaskan");
        databasehelper.addMessage(Message1);

        ChatMessage Message2 = new ChatMessage();
        Message2.setId(2);
        Message2.setId_franchise(2);
        Message2.setEmail("Betta@gmail.com");
        Message2.setMessage("Saya sangat sangat puas dengan hasil bisnis Franchise yang saya buka hingga berdiri sampai saat ini");
        databasehelper.addMessage(Message2);

        ChatMessage Message3 = new ChatMessage();
        Message3.setId(3);
        Message3.setId_franchise(3);
        Message3.setEmail("Anggi@gmail.com");
        Message3.setMessage("Mantap coy benar, g boong");
        databasehelper.addMessage(Message3);

        ChatMessage Message4 = new ChatMessage();
        Message4.setId(4);
        Message4.setId_franchise(4);
        Message4.setEmail("Anggi@gmail.com");
        Message4.setMessage("This Franchise is very very WOW");
        databasehelper.addMessage(Message4);

        ChatMessage Message5 = new ChatMessage();
        Message5.setId(5);
        Message5.setId_franchise(5);
        Message5.setEmail("Anggi@gmail.com");
        Message5.setMessage("Sungguh mengejutkan hasil yang begitu mantap");
        databasehelper.addMessage(Message5);

        ChatMessage Message6 = new ChatMessage();
        Message6.setId(6);
        Message6.setId_franchise(1);
        Message6.setEmail("Curve@gmail.com");
        Message6.setMessage("Saya sangat beruntung bekerjasama dengan Franchisor yang satu ini");
        databasehelper.addMessage(Message6);

        ChatMessage Message7 = new ChatMessage();
        Message7.setId(7);
        Message7.setId_franchise(2);
        Message7.setEmail("Banty@gmail.com");
        Message7.setMessage("Its Magic, I love it");
        databasehelper.addMessage(Message7);

        ChatMessage Message8 = new ChatMessage();
        Message8.setId(8);
        Message8.setId_franchise(3);
        Message8.setEmail("Keke@gmail.com");
        Message8.setMessage("Sungguh menjanjikan bisnis ini");
        databasehelper.addMessage(Message8);

    }

}


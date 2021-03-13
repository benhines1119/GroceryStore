package store.tests;

import java.util.Calendar;

import store.entities.Member;
import store.facade.GroceryStore;
import store.facade.Request;
import store.facade.Result;
import ui.UserInterface;

public class AutomatedTester {

	private String[] names = { "Paul", "George", "John", "Ringo", "Elton" };
	private String[] addresses = { "123 Fair Ave.", "555 Ocean Front Pkwy.", "147 W 5th St.", "10 Downing St.",
			"1600 Pennsylvania Ave. NW" };
	private String[] phones = { "3125559876", "3105551245", "6515552045", "02055590000", "2025551000" };
	private Calendar calendar = Calendar.getInstance();
	private Calendar[] dates = new Calendar[5];
	private double[] feesPaid = { 12, 13.67, 14.90, 17, 20 };
	private Member[] members = new Member[5];

	private String[] productNames = { "Milk Whole 1qt", "Milk 2% 1gal", "Milk 2% 1qt", "Milk Skim 1gal",
			"Milk Skim 1gal", "Bread Italian 1lb", "Bread French 1pc", "Eggs Fresh 12pcs", "Juice Orange 1gal",
			"Juice Apple 1gal", "Water Distilled 1gal", "Water Sparkling 1l", "Cola 2l", "Cola Can 12oz",
			"Lemon/Lime Soda 2l", "Lemon/Lime Soda Can 12oz", "Root Beer 2l", "Root Beer Can 12oz",
			"Ice Cream Vanilla 1qt", "Ice Cream Chocolate 1qt" };
	private String[] productIds = { "P-1", "P-2", "P-3", "P-4", "P-5", "P-6", "P-7", "P-8", "P-9", "P-10", "P-11",
			"P-12", "P-13", "P-14", "P-15", "P-16", "P-17", "P-18", "P-19", "P-20" };
	private int[] reorderLevel = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	private double[] currentPrice = { 1.99, 3.79, 1.99, 3.79, 3.79, 4.5, 3.75, 2.29, 4.99, 3.99, 0.89, 1.49, 1.89, 0.6,
			1.89, 0.6, 1.89, 0.6, 3.97, 3.97 };
	private int productCount = 20;

	private void makeDates() {
		for (int i = 0; i < 5; i++) {
			calendar.add(Calendar.DATE, (-15 * i));
			dates[i] = calendar;
		}
	}

	public void testEnrollMember() {
		makeDates();
		for (int index = 0; index < members.length; index++) {
			Request.instance().setMemberName(names[index]);
			Request.instance().setMemberAddress(addresses[index]);
			Request.instance().setMemberPhoneNumber(phones[index]);
			Request.instance().setMemberDateJoined(dates[index]);
			Request.instance().setMemberFeePaid(feesPaid[index]);

			Result result = GroceryStore.instance().enrollMember(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getMemberName().equalsIgnoreCase(names[index]);
			assert result.getMemberPhoneNumber().equals(phones[index]);
			assert result.getMemberDateJoined().equals(dates[index]);
			assert result.getMemberFeePaid() == feesPaid[index];
		}
	}

	public void testRemoveMember() {

	}

	public void testAddProduct() {
		for (int index = 0; index < productCount; index++) {
			Request.instance().setProductId(productIds[index]);
			Request.instance().setProductName(productNames[index]);
			Request.instance().setProductStockOnHand(0);
			Request.instance().setProductReorderLevel(reorderLevel[index]);
			Request.instance().setProductCurrentPrice(currentPrice[index]);

			Result result = GroceryStore.instance().addProduct(Request.instance());

			assert result.getResultCode() == Result.ACTION_SUCCESSFUL;
			assert result.getProductId().equalsIgnoreCase(productIds[index]);
			assert result.getProductName().equalsIgnoreCase(productNames[index]);
			assert result.getProductStockOnHand() == 0;
			assert result.getProductReorderLevel() == reorderLevel[index];
			assert result.getProductCurrentPrice() == currentPrice[index];
		}
	}

	public void testAll() {
		testEnrollMember();
		testAddProduct();
		for (int index = 1; index <= 20; index++) {
			Request.instance().setOrderId("O-" + index);
			GroceryStore.instance().processOrder(Request.instance());
		}
		UserInterface.instance().listMembers();
		UserInterface.instance().listProducts();
		System.out.println("Testing was successful!");
	}

	public static void main(String[] args) {
		new AutomatedTester().testAll();
	}

}

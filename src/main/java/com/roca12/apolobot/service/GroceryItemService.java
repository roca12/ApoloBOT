// package com.roca12.apolobot.service;
//
// import java.util.List;
//
// import org.springframework.stereotype.Controller;
//
// import com.roca12.apolobot.controller.ApplicationContextHolder;
// import com.roca12.apolobot.model.GroceryItem;
// import com.roca12.apolobot.repository.GroceryItemRepository;
//
// @Controller
// public class GroceryItemService {
//
//	private GroceryItemRepository groceryItemRepo;
//
//	public GroceryItemService() {
//		groceryItemRepo = ApplicationContextHolder.getContext().getBean(GroceryItemRepository.class);
//	}
//
//	public void createGroceryItems() {
//		System.out.println("Data creation started...");
//		groceryItemRepo.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5,
// "snacks"));
//		System.out.println("Data creation complete...");
//	}
//
//	public void showAllGroceryItems() {
//		groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
//	}
//
//	public void getGroceryItemByName(String name) {
//		System.out.println("Getting item by name: " + name);
//		GroceryItem item = groceryItemRepo.findItemByName(name);
//		System.out.println(getItemDetails(item));
//	}
//
//	public void findCountOfGroceryItems() {
//		long count = groceryItemRepo.count();
//		System.out.println("Number of documents in the collection: " + count);
//	}
//
//	public String getItemDetails(GroceryItem item) {
//		System.out.println("Item Name: " + item.getName() + ", \nQuantity: " + item.getQuantity()
//				+ ", \nItem Category: " + item.getCategory());
//		return "";
//	}
//
//	public void updateCategoryName() {
//
//		String newCategory = "munchies";
//
//		List<GroceryItem> list = groceryItemRepo.findAll();
//
//		list.forEach(item -> {
//			item.setCategory(newCategory);
//		});
//
//		List<GroceryItem> itemsUpdated = groceryItemRepo.saveAll(list);
//
//		if (itemsUpdated != null)
//			System.out.println("Successfully updated " + itemsUpdated.size() + " items.");
//	}
//
//	public void deleteGroceryItem(String id) {
//		groceryItemRepo.deleteById(id);
//		System.out.println("Item with id " + id + " deleted...");
//	}
// }

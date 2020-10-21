package com.tacocloud.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tacocloud.Ingredient;
import com.tacocloud.Ingredient.Type;
import com.tacocloud.Taco;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

//end::head[]

	
// Step 1: Create a model method to add ingredients to the list
@ModelAttribute
public void addIngredientsToModel(Model model) {
	// Step 2: Create a list of ingredients
	List<Ingredient> ingredients = Arrays.asList(
	  new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
	  new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
	  new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
	  new Ingredient("CARN", "Carnitas", Type.PROTEIN),
	  new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
	  new Ingredient("LETC", "Lettuce", Type.VEGGIES),
	  new Ingredient("CHED", "Cheddar", Type.CHEESE),
	  new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
	  new Ingredient("SLSA", "Salsa", Type.SAUCE),
	  new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
	);
	
	// Step 3: Create an array of the types from the enum
	Type[] types = Ingredient.Type.values();
	
	// Step 4: Add to model the values from the array
	for (Type type : types) {
	  model.addAttribute(type.toString().toLowerCase(),
	      filterByType(ingredients, type));
	}
}
	
//tag::showDesignForm[]
  @GetMapping
  public String showDesignForm(Model model) {
    model.addAttribute("design", new Taco());
    return "design";
  }

//end::showDesignForm[]


// Step 6: Method to handle the post request using @PostMapping
@PostMapping
public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, Model model) {
	if(errors.hasErrors()) {
		return "design";
	}
	log.info("Processing design:" + design);
	return "redirect:/orders/current";
}




//tag::filterByType[]
  // Step 5: filter the ingredient by type to display the same
  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

//end::filterByType[]
// tag::foot[]
}
// end::foot[]
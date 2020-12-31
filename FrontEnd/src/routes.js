import Home from "./pages/home";
import Dish from "./pages/dish";
import DishCategory from "./pages/dishCategory";
import OrderDetails from "./pages/orderDetails";
import Customer from "./pages/customer";
import Order from "./pages/order";
import Staff from "./pages/staff";
import Ingredient from "./pages/ingredient";
import IngredientCategory from "./pages/ingredientCategory";
import Table from "./pages/table";
import Area from "./pages/area";
import Revenue from "./pages/revenue";
const routes = [
  { path: "/", exact: true, name: "Home", component: Home },
  { path: "/home", exact: true, name: Home, component: Home },
  { path: "/dish", exact: true, name: Dish, component: Dish },
  {
    path: "/dishcategory",
    exact: true,
    name: DishCategory,
    component: DishCategory,
  },
  {
    path: "/orderdetails",
    exact: true,
    name: OrderDetails,
    component: OrderDetails,
  },
  { path: "/customer", exact: true, name: Customer, component: Customer },
  { path: "/order", exact: true, name: Order, component: Order },
  { path: "/staff", exact: true, name: Staff, component: Staff },
  { path: "/ingredient", exact: true, name: Ingredient, component: Ingredient },
  {
    path: "/ingredientcategory",
    exact: true,
    name: IngredientCategory,
    component: IngredientCategory,
  },
  { path: "/table", exact: true, name: Table, component: Table },
  { path: "/area", exact: true, name: Area, component: Area },
  { path: "/revenue", exact: true, name: Revenue, component: Revenue },
];

export default routes;

Project Overview:

The E-Commerce Management System is a Java-based application designed to inventory management, user registration, online shopping, and order processing.
It provides a text-based user interface that allows users to interact with the system. The project includes the following key features:

1. User Registration and Login:

New users can register by providing their first name, last name, email, and password.
Registered users can log in using their email and password.
The system validates user inputs, ensuring data accuracy.

2. Inventory Management:

The system manages an inventory of different product categories, including Electronics, Fragile Items, and Grocery Items.
Products are stored in a CSV file for easy retrieval.

3. Viewing Products:

Users can view products based on their categories.
The application provides separate views for Electronics, Fragile Items, and Grocery Items.
This feature is accessible to logged-in users.

4. Shopping Cart:

Users can add products to their shopping cart.
The cart displays added items, their quantities, and the total cart value.
Users can also remove products from the cart or view their cart contents.

5. Order Creation:

Users can create orders that include the items in their shopping cart.
Each order is associated with the user who placed it and includes payment details.
The application validates and processes payments securely.

6. Admin Mode:

To enter Admin mode use login "admin" and password "admin".
The system includes an admin mode that allows administrators to manage and maintain inventory items and user data.
Admins can add, update, and delete products and view user information.

How to Run the Application:

Ensure you have Java installed on your system.
Import the project into a Java IDE (e.g., IntelliJ IDEA or Eclipse).
Compile the Java classes.
Run the main class, "ECommerceApp," to start the application.
Use the console interface to navigate the application's features.

Use Cases:

Customers can register, log in, view products, add items to their shopping cart, and create orders.
Administrators can access the admin mode to manage inventory and user data.

Data:

When the application starts, it reads data from the file to populate its internal data structures.
All data (products, damaged goods, users, orders) is saved to a file the moment the user selects option 7 - Exit.

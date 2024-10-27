# Supermarket Tycoon

Welcome to **Supermarket Tycoon**, a simulation game where you manage and grow your own supermarket!

This game is a simulation of managing a supermarket, where players balance inventory, set prices, and make upgrades to maximize profits and avoid bankruptcy. The gameplay is simple yet challenging, requiring strategic planning to optimize daily actions and manage customer flow. The main interface is divided into four sections, each providing essential information and controls to manage your supermarket.

## Features

- **Inventory System**: Keep track of your inventory, adjust store prices to attract more customers.
- **Upgrade Your Market**: Buy licenses to sell more products, upgrade your market to be able to store more type of products and increase your daily action limit.
- **Customer Satisfaction**: Keep your customers happy with proper item pricing; the better price you set, the more customers you will get each day.
- **Financial Management**: Balance your budget, set prices, and maximize profits.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/FreeTurk/SupermarketTycoon
    ```
2. Install gradle on your system

## Usage

Run the game:
```sh
gradle run
```

**Build File** is located in
   ```sh
   app/build/libs
   ```


## Game Overview

The game interface consists of four primary sections:

1. **Top Bar**  
   Displays the following:
   - **Money:** The player’s current balance.
   - **Date:** The in-game date.
   - **Energy:** The current energy level, affecting the number of actions available.
   - **Skip Button:** Allows the player to advance to the next day.

2. **Inventory Display (Left Panel)**  
   A table listing:
   - **Stored Items:** Displays all stocked items.
   - **Prices:** Shows the current set price for each item.
   - **Expiration Time:** Indicates the time remaining before each item expires.

3. **Middle Section (Customer and Upgrade Display)**  
   - **Bought Upgrades:** Displays all upgrades currently owned.
   - **Customer Count:** Visual representation of the daily customer count.

4. **Upgrade Menu (Right Panel)**  
   Lists available upgrades and licenses for purchase:
   - **Supermarket Upgrades:** Improve various aspects of the store.
   - **Product Licenses:** Expands the product range by allowing the sale of new items like seafood.
   - **Restock Option:** Allows restocking to maintain product availability.

## Gameplay Mechanics

The game is straightforward but requires strategic mastery to succeed. Here’s how it works:

- **Product Restocking:** The player needs to restock products to ensure a continuous supply of goods to sell. 
- **Daily Customer Count:** The number of daily customers is influenced by:
  - **Expiration Date:** Products closer to expiration may affect customer interest.
  - **Pricing:** Competitive pricing increases customer attraction.
  
- **Setting Prices:**  
   - To set or update prices, double-click on an item’s price, edit the amount, and press "Enter" to save.

- **Licenses and Seasonal Items:**  
   - Buy licenses (e.g., seafood) to expand the range of available products.
   - Seasonal items, like ice cream in winter, boost sales during specific times.

- **Energy and Actions:**  
   - Each day, players have a limit of 10 actions, which can be increased by purchasing energy upgrades.

## Objective

The goal is to make efficient decisions daily to avoid bankruptcy. Key objectives include:
- **Earning Profits:** Ensure a positive daily cash flow.
- **Avoiding Bankruptcy:** There’s a recurring expense of $100 every 10 days, so ensure earnings exceed this amount to stay afloat.

---

## Topics Of Choice

## Git

**Definition:**  
Git is a DevOps tool used for source code management. It allows developers to manage and track changes in code, enabling rollbacks to previous versions so that no code is lost.

**Importance of Using Git:**  
Given the complexity of the game's system, especially on the backend, multiple updates and patches will likely be needed to address potential bugs and optimize gameplay. When tackling bugs or implementing updates, there is a risk of losing the main source code, which could compromise the development process. Git mitigates this risk by:

- **Version Control:** Allowing developers to view and roll back to previous versions of the codebase as needed, preserving the integrity of the project.
- **Collaborative Development:** Enabling multiple developers to work on the same project simultaneously using branches. Branching allows for isolated changes and code merging, ensuring smoother collaboration.

By using Git, developers can build on the project safely, make patches, and implement gameplay changes without risking code loss. It supports robust project management and minimizes development risks.

---

## UX (User Experience)

**Definition:**  
UX, short for User Experience, refers to how a user interacts with a product or service, encompassing aspects like ease of use, accessibility, and overall satisfaction with the interface.

**Importance of UX in the Game:**  
The game's design requires strategic planning and micromanagement, presenting a learning curve for new players. To ensure players remain engaged rather than frustrated, UX is a critical focus. Key UX considerations for the game include:

- **Control and Clarity:** Since players need absolute control over supermarket management, a refined user interface is essential to facilitate engagement and comprehension.
- **Intuitive Design:** The interface should be aesthetically pleasing, easy to navigate, and structured for clarity. Key functions should be placed appropriately to enhance positional memory, helping players quickly locate frequently used controls.
- **Enhanced Experience:** An accessible interface allows players to focus on strategy rather than struggling with navigation, improving the overall game experience.

A well-designed UX is essential for maintaining player interest, reducing the learning curve, and ensuring that strategic gameplay feels rewarding rather than cumbersome.


## Contact

For any inquiries or feedback, please contact us at [h.m.celik@student.tue.nl](mailto:h.m.celik@student.tue.nl) or [e.oven@student.tue.nl](mailto:e.oven@student.tue.nl) .

Enjoy managing your supermarket!

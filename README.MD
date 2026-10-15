## Project Plan & Architecture Sketch

### Final Project
* **Name:** Oleksandr Mekhed
* **Course:** Programming with Java (DEV26M_PJ-2)

---

### Project Idea
A Vehicle Rental System for a rental company. The application manages a fleet of vehicles (cars, electric cars, trucks and electric scooters), allows renting and returning vehicles, calculates rental and insurance costs, tracks battery levels of electric vehicles, and generates a fleet report via an interactive console interface.

---

### Superclass
* **Name:** `Vehicle` (Abstract class)
* **Fields:**
    * `String id` (unique, non-empty, (Car – "C001", ElectricScooter – "ES011", Truck – "T019", ElectricCar – 
      "EC031" – by convention, not enforced by the code))
    * `String brand`
    * `String model`
    * `double pricePerDay`
    * `boolean rented`
* **Methods:**
    * `validateDays(int days)` – Validates the number of rental days. Reused by all subclasses.
    * `calculateRentalCost(int days)` – Abstract. Calculates the total rental cost for a given number of days.
    * `getDetails()` – Returns a summary representation of the vehicle.
    * `rent()` – Marks the vehicle as rented. Throws an exception if it is already rented.
    * `returnVehicle()` – Marks the vehicle as available again.

---

### Subclasses
1. **`Car`**
    * **Distinct features:** Represents a regular car. Contains `int seats`.
    * **Overridden methods:**
        * `calculateRentalCost(int days)` – `pricePerDay` * `days`.
        * `calculateInsuranceCost(int days)` – `INSURANCE_PER_DAY` * `days` (days are validated first).
        * `getDetails()` – Displays general details and number of seats.

2. **`ElectricCar`**
    * **Distinct features:** Represents an electric car. Contains `int batteryPercent` (0–100) and `int rangeKm`.
    * **Overridden methods:**
        * `calculateRentalCost(int days)` – `pricePerDay` * `days` with a 10% eco discount.
        * `getBatteryPercent()` – Returns the current battery percentage.
        * `rent()` – Refuses to rent the vehicle if `batteryPercent` is below 20%.
        * `calculateInsuranceCost(int days)` – `INSURANCE_PER_DAY` * `days` (days are validated first).
        * `charge()` – Charges the battery to full (100%).
        * `getDetails()` – Displays general details, battery level and range.

3. **`Truck`**
    * **Distinct features:** Represents a truck. Contains `double loadCapacityKg`.
    * **Overridden methods:**
        * `calculateRentalCost(int days)` – `pricePerDay` * `days` plus a daily surcharge based on load capacity.
        * `calculateInsuranceCost(int days)` – `INSURANCE_PER_DAY` * `days` (days are validated first).
        * `getDetails()` – Displays general details and load capacity. 

4. **`ElectricScooter`**
    * **Distinct features:** Represents an electric scooter. Contains `int batteryPercent` (0–100) and `int maxSpeedKmh`.
    * **Overridden methods:**
        * `calculateRentalCost(int days)` – `pricePerDay` * `days`, with a maximum rental period of 7 days.
        * `getBatteryPercent()` – Returns the current battery percentage.
        * `rent()` – Refuses to rent the scooter if `batteryPercent` is below 20%.
        * `charge()` – Charges the battery to full (100%).
        * `getDetails()` – Displays general details, battery level and maximum speed.

---

### Interfaces
1. **`Insurable`**
    * **Method:** `double calculateInsuranceCost(int days)`
    * **Implemented by:** `Car`, `ElectricCar` and `Truck`. *(Electric scooters are not insured.)*

2. **`Chargeable`**
    * **Methods:** `int getBatteryPercent()`, `void charge()`.
    * **Constants:** `FULL_BATTERY` (100) and `MIN_BATTERY_TO_RENT` (20), shared by all electric vehicles.
    * **Implemented by:** `ElectricCar` and `ElectricScooter`.

---

### Menu Actions
1. **Snow All Vehicles:** Display all vehicles in the fleet.
2. **Add Vehicle:** Create and register a new Car, ElectricCar, Truck or ElectricScooter.
3. **Rent / Return Vehicle:** Rent out an available vehicle or return a rented one.
4. **Search Vehicle:** Search for vehicles by `brand` or `type` (Car, ElectricCar, Truck, ElectricScooter).
5. **Remove Vehicle:** Delete a vehicle from the fleet using unique `id`.
6. **Generate Fleet Report (Custom Action):** Display expected rental income, number of rented vehicles, vehicles that 
   need charging and total insurance cost.
0. **0 - Exit Program:** Quit the application.

---

### Exception Scenarios
1. **Invalid Constructor Input:** If a user attempts to create a vehicle with invalid data (empty brand, negative `pricePerDay `or` batteryPercent `outside 0–100), the constructor throws an `IllegalArgumentException` which is caught and handled.
2. **Duplicate or Unknown ID:** Adding a vehicle with an existing `id`, or removing a vehicle that does not exist or is currently rented, is reported with an informative message.
3. **Invalid Rental Action:** Renting a vehicle that is already rented, or an electric vehicle with a battery below 20%, is refused with a clear error message.
4. **Invalid Menu Input:** If a user inputs text or an empty answer when a number is expected, `NumberFormatException` is handled gracefully without crashing the program.

---

### Program Structure
* `Main`: Starts the program only.
* `RentalMenu`: Interaction logic only (reading input, printing output).
* `RentalService`: Holds the `ArrayList<Vehicle>`. Contains show all, add, remove, search and report logic.
* Domain classes (`Vehicle` and subclasses): Contain all validation and calculations.

---

### Motivation / Reflection & Design Rationale
The structure separates responsibilities: domain classes (`Vehicle` and subclasses) hold all validation and 
calculations, `RentalService` holds the fleet and business rules, and `RentalMenu` only handles input and output. I 
used an abstract superclass for shared state, and interfaces (`Insurable`, `Chargeable`) for capabilities that only 
some vehicles have. I considered for example an intermediate `ElectricVehicle` class and a percentage-based insurance, 
but chose a flat hierarchy and fixed rates for simplicity.

Full reasoning for each class: **[Reflection & Design Rationale](docs/Reflection_Design.MD)**
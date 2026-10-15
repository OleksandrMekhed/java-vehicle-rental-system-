### Reflection & Design Rationale

---

#### Vehicle (abstract superclass)
* **Abstract class instead of an interface.** All vehicles share state (`id`, `brand`, `model`, `pricePerDay`, `rented`) and behaviour (`rent()`, `returnVehicle()`). An interface cannot hold fields or shared logic, so an abstract class avoids duplicating this code in every subclass. Interfaces are used for capabilities that only some classes have (`Insurable`, `Chargeable`).
* **`calculateRentalCost()` is abstract.** Every vehicle type is priced differently, so there is no sensible default. Making it abstract forces each subclass to implement it and enables polymorphism.
* **Validation in setters, called from the constructor.** Validation exists in one place only, so the constructor 
  and later changes follow the same rules. The setters in `Vehicle` are `final` because they are called from the constructor and the class is designed for inheritance, a subclass overriding them would run before the subclass is initialised. Subclass setters are not `final` because those classes are leaf classes.
* **`rented` is not a constructor parameter and has no setter.** A new vehicle is always available, and the state can only change through `rent()` and `returnVehicle()`. This prevents the object from ending up in an inconsistent state.
* **`IllegalStateException` for rent/return errors.** Renting an already rented vehicle is not a bad argument but a bad object state, so `IllegalStateException` describes it more accurately than `IllegalArgumentException`.
* **`validateDays()` helper.** Day validation is placed in a `protected static` method so it is not duplicated in every subclass.
* **`getDetails()` has no side effects.** It only reads state (through `isRented()`) and never changes it. Subclasses extend it with `super.getDetails()` instead of rewriting it.
* **`String id` instead of `int`.** Readable id such as "V001" are more natural for a fleet. Uniqueness is checked in `RentalService`, because a single `Vehicle` does not know about the other vehicles.
* **`double` for money.** `BigDecimal` is more precise, but `double` is simpler and sufficient for this project. Output is formatted to two decimals.

---

#### Car (subclass)
* `Car` adds only what is specific to it (`seats`, validated to be greater than zero) and reuses everything else through inheritance.
* `calculateRentalCost()` and `getDetails()` are overridden, so the correct version runs automatically when a `Vehicle` reference points to a `Car`.
* Calling `validateDays()` first keeps day validation consistent across all vehicle types.

---

#### ElectricCar (subclass)
* **`rent()` extends the parent logic instead of replacing it.** The battery is checked first, then `super.rent()` runs. This way the rented check and the state change are not duplicated.
* **`IllegalStateException` for a low battery.** The argument (the vehicle) is valid, but its current state does not allow renting, so `IllegalStateException` fits better than `IllegalArgumentException`. The minimum is 20%, so a car with exactly 20% can still be rented.
* **Constants for the rules.** `ECO_DISCOUNT = 0.10` is a `private static final` constant because it is a rule of this class only. `MIN_BATTERY_TO_RENT` is shared with `ElectricScooter`, so it lives in `Chargeable`. The price is multiplied by `(1 - ECO_DISCOUNT)`, which keeps the meaning 10% off clear.
* **Renting does not drain the battery.** Only `charge()` changes the battery level. Keeping these separate makes the fleet report simpler and avoids hidden side effects.

---

#### Truck (subclass)
* **Surcharge based on load capacity.** The daily price is `pricePerDay + loadCapacityKg * 0.02`, so heavier trucks cost more. The rate is a constant because it is the same for all trucks.
* **Only what differs is overridden.** `Truck` does not override `rent()`, because it follows the default rules from `Vehicle`.

---

#### ElectricScooter (subclass)
* **Business rule inside the domain class.** The 7-day limit (`MAX_RENTAL_DAYS`) lives in `calculateRentalCost()`, not in the menu, so the rule is enforced no matter how the class is used.
* **`IllegalArgumentException` for too many days.** The problem is the argument passed to the method, and the message shows both the limit and the requested value.
* **The constructor uses the setters.** Validation exists in one place, so an invalid b#### Car (subclass)

---

#### Insurable (interface)
* **Interface instead of a method in `Vehicle`.** Not every vehicle is insured: electric scooters are not. If `calculateInsuranceCost()` lived in `Vehicle`, the scooter would have to implement it with a meaningless result (zero or an exception). An interface describes a capability that only some classes have, so only `Car`, `ElectricCar` and `Truck` implement it.
* **Polymorphic use.** The fleet report treats every insured vehicle the same way through an `Insurable` reference and calls `calculateInsuranceCost()` without knowing the concrete type. A new insurable vehicle type only needs `implements Insurable`, and `RentalService` does not have to change.
* **Fixed daily rate as a named constant.** Each class has its own `INSURANCE_PER_DAY`. The order reflects the assumed risk and value of each type. The values are arbitrary and easy to change in one place.
* **Alternative: percentage of the rental cost.** I considered this, but it would tie insurance to the rental price and to discounts such as the eco discount in `ElectricCar`. A fixed rate keeps the two calculations independent and easier to understand.
* **Day validation reused.** Each implementation calls `validateDays()` from `Vehicle` first, so invalid input is rejected in the same way as in `calculateRentalCost()`.
* **Interface instead of an intermediate abstract class.** An `InsurableVehicle` class between `Vehicle` and its subclasses would work, but Java allows only one superclass. `ElectricCar` also needs `Chargeable`, and a class can implement several interfaces at once.

---

#### Chargeable (interface)
* **Interface instead of a shared superclass.** `ElectricCar` and `ElectricScooter` share only the battery, and Java allows only one superclass. `ElectricCar` also implements `Insurable`, so an interface lets it combine both capabilities without making the hierarchy deeper.
* **Alternative: for exampl an `ElectricVehicle` abstract class.** It would remove the repeated battery check in `rent
()`, but it would add a level to the hierarchy and still could not be combined with other capabilities. I accepted the small duplication to keep the hierarchy flat.
* **Polymorphic use.** The fleet report and the charge logic loop over `Chargeable` references and call `charge()` without knowing the concrete type. A new electric vehicle type only needs `implements Chargeable`.
* **`charge()` fills the battery to full.** It reuses `setBatteryPercent()`, so the 0-100 validation stays in one place. Only `charge()` raises the battery level and renting never lowers it, which keeps the two actions independent.
* **Two independent interfaces.** `Insurable` and `Chargeable` describe different capabilities. `ElectricCar` implements both, `Car` and `Truck` only `Insurable`, and `ElectricScooter` only `Chargeable`. A single combined interface would force classes to implement methods they do not need.
* **Battery constants live in `Chargeable`.** `FULL_BATTERY` and `MIN_BATTERY_TO_RENT` are rules shared by every electric vehicle, so they are defined once in the interface (fields in an interface are implicitly `public static final`) instead of being duplicated in `ElectricCar` and `ElectricScooter`. If a rule changes, only one line changes.

---

#### RentalService (fleet management)
* **No input or output in the service.** `RentalService` never uses `Scanner` or `System.out`. Problems are reported by throwing exceptions, and the menu decides how to show them. This keeps business rules in the domain layer and the menu purely interactive.
* **`ArrayList` for the fleet.** The fleet is searched and iterated far more often than it is changed, and an `ArrayList` is simple and fast for that. Vehicles are found by looping, so no extra data structure is needed.
* **`getAll()` returns a copy.** The internal list cannot be changed from outside, so every change goes through `addVehicle()` and `removeVehicle()`, where the rules are checked.
* **`findById()` is reused.** ID validation and the "not found" error exist in one place and are used by `removeVehicle()`, `rentVehicle()` and `returnVehicle()`.
* **Unique ID are checked in the service.** A single `Vehicle` does not know about the other vehicles, so the duplicate check belongs to the class that owns the list.
* **A rented vehicle cannot be removed.** This is a state problem, so it throws `IllegalStateException`, not `IllegalArgumentException`.
* **`searchByType()` uses the class name.** Comparing `getClass().getSimpleName()` means a new vehicle subclass is found automatically, without a chain of `instanceof` checks.
* **`instanceof` only for capabilities.** The report uses `instanceof Insurable` and `instanceof Chargeable` to treat different classes through the same interface. The service never checks for concrete types such as `Car` or `Truck`, so new types can be added without changing it.
* **An empty search result is not an error.** The service returns an empty list, and the menu decides what to tell the user.
* **Charging only concerns vehicles in the fleet.** `chargeAll()` and `getVehiclesNeedingCharge()` skip rented vehicles, because a vehicle that is out with a customer cannot be charged by the company, and its battery level is not the company's concern until it is returned. Both methods use the same rule, so the report always matches what charging would actually do.

---

#### RentalMenu and Main (interaction layer)
* **The menu contains interaction logic only.** `RentalMenu` asks questions, calls `RentalService` and prints results. It contains no calculations and no business rules, so the console could be replaced by another interface without touching the domain classes.
* **Input is read as text and parsed.** Every answer is read with `nextLine()` and converted with `Integer.parseInt()` or `Double.parseDouble()`, not with `nextInt()`. This avoids leftover newline problems in the `Scanner`, and a letter or an empty answer cannot crash the program.
* **Specific `catch` blocks.** The read helpers catch `NumberFormatException` and ask again. The actions catch `IllegalArgumentException | IllegalStateException` and show `getMessage()`. There is no general `catch (Exception e)`, so unexpected programming errors are not hidden.
* **Helper methods avoid repetition.** `readInt()`, `readText()` and `readDouble()` are reused by every action, and `printVehicles()` is reused by "show all" and "search". `readDouble()` also rejects `NaN` and `Infinity`, which `Double.parseDouble()` would accept.
* **Validation is checked before the domain is called.** The menu choice is checked before asking for an ID, so an invalid choice does not force the user to type data that is thrown away. Domain errors (unknown ID, low battery) are not duplicated in the menu: they come from the domain classes as exceptions.
* **The report asks before it charges.** The fleet report shows what needs charging and only calls `chargeAll()` after the user confirms, because a report should not change data by itself.
* **`Main` only starts the program.** It creates `RentalService` and `RentalMenu` and calls `run()`. Dependencies are passed through the constructor, so the menu does not create its own service.
* **Alternative: a `switch` on vehicle type in the menu.** Creating a vehicle needs a `switch` on type, because different classes need different questions. attery level or speed cannot enter through the constructor.
* **Design alternative: shared `ElectricVehicle` class.** `ElectricCar` and `ElectricScooter` repeat the battery check in `rent()`. An intermediate abstract class could remove this, but it would make the hierarchy deeper. I kept the hierarchy flat and share the behaviour through the `Chargeable` interface instead the duplication is small.

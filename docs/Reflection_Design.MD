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


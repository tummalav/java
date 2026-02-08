/**
 * Java vs C++ Object-Oriented Programming Differences
 *
 * Complete comparison of OOP features that differ between Java and C++
 *
 * Topics Covered:
 * 1. Multiple Inheritance (C++ YES, Java NO - uses interfaces)
 * 2. Virtual Functions (Java all virtual, C++ explicit)
 * 3. Operator Overloading (C++ YES, Java NO)
 * 4. References (lvalue/rvalue in C++, hidden in Java)
 * 5. Value vs Reference Semantics
 * 6. Copy/Move Constructors (C++ YES, Java NO)
 * 7. Destructors (C++ YES, Java NO - has finalizers/GC)
 * 8. Memory Management (C++ manual, Java GC)
 * 9. Default Constructors
 * 10. Access Control Differences
 *
 * Author: Complete OOP Comparison Guide
 * Date: February 8, 2026
 */

public class Java_vs_CPP_OOP_Differences {

    //=============================================================================
    // 1. MULTIPLE INHERITANCE
    //=============================================================================

    /**
     * C++: Supports MULTIPLE INHERITANCE (can inherit from multiple classes)
     * Java: NO multiple inheritance (can only extend ONE class)
     *       But can implement MULTIPLE interfaces
     */
    static void demonstrateMultipleInheritance() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  1. MULTIPLE INHERITANCE                                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ Multiple Inheritance:");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Trader { };");
        System.out.println("class Analyst { };");
        System.out.println("class QuantTrader : public Trader, public Analyst {");
        System.out.println("    // ✅ Inherits from BOTH Trader and Analyst");
        System.out.println("};");

        System.out.println("\nJava Approach (Single Inheritance + Interfaces):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("interface Tradable { void trade(); }");
        System.out.println("interface Analyzable { void analyze(); }");
        System.out.println("class Trader { }");
        System.out.println("class QuantTrader extends Trader implements Tradable, Analyzable {");
        System.out.println("    // ✅ Extends ONE class, implements MULTIPLE interfaces");
        System.out.println("}");

        // Java example
        QuantTrader qt = new QuantTrader();
        qt.trade();
        qt.analyze();

        System.out.println("\nKey Difference:");
        System.out.println("• C++:  Multiple class inheritance (can cause diamond problem)");
        System.out.println("• Java: Single class inheritance + multiple interfaces (avoids diamond problem)");

        System.out.println("\nDiamond Problem (C++ can have, Java avoids):");
        System.out.println("     Base");
        System.out.println("    /    \\");
        System.out.println("  Left  Right");
        System.out.println("    \\    /");
        System.out.println("   Derived");
        System.out.println("• C++:  Must use virtual inheritance to resolve");
        System.out.println("• Java: Impossible with classes, safe with interfaces");
    }

    // Java interfaces (workaround for multiple inheritance)
    interface Tradable {
        void trade();
    }

    interface Analyzable {
        void analyze();
    }

    static class Trader {
        protected String name;
        public void executeTrade() {
            System.out.println("Executing trade...");
        }
    }

    static class QuantTrader extends Trader implements Tradable, Analyzable {
        @Override
        public void trade() {
            System.out.println("Quant trading with algorithms");
        }

        @Override
        public void analyze() {
            System.out.println("Analyzing market data");
        }
    }

    //=============================================================================
    // 2. VIRTUAL FUNCTIONS
    //=============================================================================

    /**
     * C++: Methods are NON-VIRTUAL by default (must use 'virtual' keyword)
     * Java: ALL methods are VIRTUAL by default (except final, static, private)
     */
    static void demonstrateVirtualFunctions() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  2. VIRTUAL FUNCTIONS                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ (Explicit virtual keyword required):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Order {");
        System.out.println("    void process() { }          // ❌ NOT virtual (static binding)");
        System.out.println("    virtual void execute() { }  // ✅ Virtual (dynamic binding)");
        System.out.println("};");
        System.out.println("class LimitOrder : public Order {");
        System.out.println("    void process() { }          // Hides base method (NOT override!)");
        System.out.println("    void execute() override { } // ✅ Overrides base method");
        System.out.println("};");

        System.out.println("\nJava (All methods virtual by default):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Order {");
        System.out.println("    void process() { }          // ✅ Virtual (dynamic binding)");
        System.out.println("    final void validate() { }   // ❌ NOT virtual (cannot override)");
        System.out.println("    static void count() { }     // ❌ NOT virtual (class method)");
        System.out.println("    private void internal() { } // ❌ NOT virtual (not inherited)");
        System.out.println("}");

        // Java demonstration
        Order order = new LimitOrder();
        order.process();  // Calls LimitOrder.process() (virtual dispatch)

        System.out.println("\nKey Difference:");
        System.out.println("• C++:  Opt-in polymorphism (declare virtual explicitly)");
        System.out.println("• Java: Opt-out polymorphism (all virtual except final/static/private)");

        System.out.println("\nPerformance:");
        System.out.println("• C++:  Non-virtual calls = direct call (faster, ~1ns)");
        System.out.println("        Virtual calls = vtable lookup (~5-10ns)");
        System.out.println("• Java: All calls through vtable (~5-10ns, but JIT optimizes)");
    }

    static class Order {
        public void process() {
            System.out.println("Order.process() called");
        }

        // final = NOT virtual (cannot be overridden)
        public final void validate() {
            System.out.println("Validating order...");
        }

        // static = NOT virtual (class method)
        public static void count() {
            System.out.println("Counting orders...");
        }

        // private = NOT virtual (not inherited)
        private void internal() {
            System.out.println("Internal method");
        }
    }

    static class LimitOrder extends Order {
        @Override
        public void process() {  // ✅ Overrides (virtual dispatch)
            System.out.println("LimitOrder.process() called");
        }

        // Cannot override final method
        // public void validate() { }  // ❌ Compile error!
    }

    //=============================================================================
    // 3. OPERATOR OVERLOADING
    //=============================================================================

    /**
     * C++: Supports OPERATOR OVERLOADING
     * Java: Does NOT support operator overloading (except String + for concatenation)
     */
    static void demonstrateOperatorOverloading() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  3. OPERATOR OVERLOADING                                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ Operator Overloading (Supported):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Price {");
        System.out.println("    double value;");
        System.out.println("public:");
        System.out.println("    Price operator+(const Price& other) {");
        System.out.println("        return Price(value + other.value);");
        System.out.println("    }");
        System.out.println("    Price operator*(double multiplier) {");
        System.out.println("        return Price(value * multiplier);");
        System.out.println("    }");
        System.out.println("    bool operator<(const Price& other) {");
        System.out.println("        return value < other.value;");
        System.out.println("    }");
        System.out.println("};");
        System.out.println("// Usage:");
        System.out.println("Price p1(100.0), p2(50.0);");
        System.out.println("Price total = p1 + p2;           // ✅ Uses operator+");
        System.out.println("Price doubled = p1 * 2;          // ✅ Uses operator*");
        System.out.println("if (p1 < p2) { }                 // ✅ Uses operator<");

        System.out.println("\nJava Approach (No operator overloading, use methods):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Price {");
        System.out.println("    private double value;");
        System.out.println("    public Price add(Price other) {");
        System.out.println("        return new Price(value + other.value);");
        System.out.println("    }");
        System.out.println("    public Price multiply(double multiplier) {");
        System.out.println("        return new Price(value * multiplier);");
        System.out.println("    }");
        System.out.println("    public boolean lessThan(Price other) {");
        System.out.println("        return value < other.value;");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("// Usage:");
        System.out.println("Price p1 = new Price(100.0), p2 = new Price(50.0);");
        System.out.println("Price total = p1.add(p2);        // ❌ Cannot use +");
        System.out.println("Price doubled = p1.multiply(2);  // ❌ Cannot use *");
        System.out.println("if (p1.lessThan(p2)) { }         // ❌ Cannot use <");

        // Java example
        Price p1 = new Price(100.0);
        Price p2 = new Price(50.0);
        Price total = p1.add(p2);
        System.out.println("\nJava result: " + p1.value + " + " + p2.value + " = " + total.value);

        System.out.println("\nKey Difference:");
        System.out.println("• C++:  Supports operator overloading (can make code cleaner)");
        System.out.println("• Java: NO operator overloading (must use explicit methods)");
        System.out.println("• Exception: String + is built-in for concatenation in Java");

        System.out.println("\nWhy Java doesn't have it:");
        System.out.println("• Simplicity: Avoid complex/confusing operator semantics");
        System.out.println("• Consistency: All operations are method calls");
        System.out.println("• Safety: No hidden behavior in operators");
    }

    static class Price {
        double value;

        public Price(double value) {
            this.value = value;
        }

        public Price add(Price other) {
            return new Price(this.value + other.value);
        }

        public Price multiply(double multiplier) {
            return new Price(this.value * multiplier);
        }

        public boolean lessThan(Price other) {
            return this.value < other.value;
        }
    }

    //=============================================================================
    // 4. REFERENCES (lvalue/rvalue)
    //=============================================================================

    /**
     * C++: Explicit lvalue references (&) and rvalue references (&&)
     * Java: All object variables are HIDDEN REFERENCES (no explicit syntax)
     */
    static void demonstrateReferences() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  4. REFERENCES (lvalue/rvalue)                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ References (Explicit):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("// Lvalue reference (refers to existing object)");
        System.out.println("Order order;");
        System.out.println("Order& ref = order;              // ✅ Lvalue reference");
        System.out.println("ref.process();                   // Same as order.process()");
        System.out.println();
        System.out.println("// Rvalue reference (for temporary objects, move semantics)");
        System.out.println("Order&& rref = createOrder();    // ✅ Rvalue reference");
        System.out.println("Order moved = std::move(order);  // ✅ Move semantics");
        System.out.println();
        System.out.println("// Function parameters");
        System.out.println("void process(Order& order);      // Pass by lvalue reference");
        System.out.println("void process(Order&& order);     // Pass by rvalue reference");

        System.out.println("\nJava References (Hidden/Implicit):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("// ALL object variables are references (hidden)");
        System.out.println("Order order = new Order();       // 'order' is a REFERENCE");
        System.out.println("Order ref = order;               // 'ref' points to SAME object");
        System.out.println("ref.process();                   // Same object as 'order'");
        System.out.println();
        System.out.println("// NO rvalue references");
        System.out.println("// NO move semantics");
        System.out.println("// NO explicit reference syntax (&, &&)");
        System.out.println();
        System.out.println("// Function parameters (always pass by reference for objects)");
        System.out.println("void process(Order order);       // 'order' is a reference");

        // Java demonstration
        OrderObj o1 = new OrderObj(100);
        OrderObj o2 = o1;  // o2 references SAME object as o1
        o2.price = 200;
        System.out.println("\nJava reference demonstration:");
        System.out.println("o1.price: " + o1.price);  // 200 (same object!)
        System.out.println("o2.price: " + o2.price);  // 200

        System.out.println("\nKey Difference:");
        System.out.println("• C++:  Explicit references (&), rvalue references (&&)");
        System.out.println("        Can pass by value, reference, or pointer");
        System.out.println("• Java: ALL objects are references (hidden)");
        System.out.println("        NO lvalue/rvalue distinction");
        System.out.println("        NO move semantics");
        System.out.println("        Primitives are ALWAYS by value");
    }

    static class OrderObj {
        int price;

        public OrderObj(int price) {
            this.price = price;
        }
    }

    //=============================================================================
    // 5. VALUE vs REFERENCE SEMANTICS
    //=============================================================================

    /**
     * C++: Can choose value or reference semantics
     * Java: Primitives are VALUE, Objects are REFERENCE
     */
    static void demonstrateValueVsReference() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  5. VALUE vs REFERENCE SEMANTICS                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ (Flexible - can choose):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("// Value semantics (copy)");
        System.out.println("Order o1;");
        System.out.println("Order o2 = o1;              // ✅ COPY (separate objects)");
        System.out.println();
        System.out.println("// Reference semantics (alias)");
        System.out.println("Order& ref = o1;            // ✅ REFERENCE (same object)");
        System.out.println();
        System.out.println("// Pointer semantics");
        System.out.println("Order* ptr = &o1;           // ✅ POINTER (address)");
        System.out.println();
        System.out.println("// Heap allocation");
        System.out.println("Order* heap = new Order();  // ✅ Heap object (manual delete)");

        System.out.println("\nJava (Fixed - primitives value, objects reference):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("// Primitives: ALWAYS value semantics");
        System.out.println("int x = 10;");
        System.out.println("int y = x;                  // ✅ COPY (separate values)");
        System.out.println("y = 20;                     // x is still 10");
        System.out.println();
        System.out.println("// Objects: ALWAYS reference semantics");
        System.out.println("Order o1 = new Order();     // 'o1' is REFERENCE (heap)");
        System.out.println("Order o2 = o1;              // 'o2' is REFERENCE (SAME object)");
        System.out.println("o2.price = 200;             // o1.price is also 200!");
        System.out.println();
        System.out.println("// ALL objects on heap (GC managed)");
        System.out.println("// NO stack objects for user-defined types");
        System.out.println("// NO manual delete/free");

        // Java demonstration
        System.out.println("\nJava Demonstration:");

        // Primitives (value semantics)
        int x = 10;
        int y = x;
        y = 20;
        System.out.println("Primitives (value): x=" + x + ", y=" + y);  // x=10, y=20

        // Objects (reference semantics)
        OrderObj o1 = new OrderObj(100);
        OrderObj o2 = o1;  // Same object!
        o2.price = 200;
        System.out.println("Objects (reference): o1.price=" + o1.price +
                          ", o2.price=" + o2.price);  // Both 200!

        System.out.println("\nSummary:");
        System.out.println("┌─────────────────┬──────────────┬──────────────────────┐");
        System.out.println("│ Type            │ C++          │ Java                 │");
        System.out.println("├─────────────────┼──────────────┼──────────────────────┤");
        System.out.println("│ Primitives      │ Value        │ Value                │");
        System.out.println("│ User objects    │ Value/Ref    │ Reference (ALWAYS)   │");
        System.out.println("│ Stack objects   │ YES          │ NO (primitives only) │");
        System.out.println("│ Heap objects    │ Explicit     │ Automatic (new)      │");
        System.out.println("│ References      │ Explicit (&) │ Hidden (all objects) │");
        System.out.println("└─────────────────┴──────────────┴──────────────────────┘");
    }

    //=============================================================================
    // 6. COPY AND MOVE CONSTRUCTORS
    //=============================================================================

    /**
     * C++: Compiler generates copy/move constructors automatically
     * Java: NO copy/move constructors (must implement clone() manually)
     */
    static void demonstrateCopyMove() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  6. COPY AND MOVE CONSTRUCTORS                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ (Automatic copy/move constructors):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Order {");
        System.out.println("    // Compiler automatically generates:");
        System.out.println("    Order(const Order& other);        // ✅ Copy constructor");
        System.out.println("    Order(Order&& other);             // ✅ Move constructor (C++11)");
        System.out.println("    Order& operator=(const Order&);   // ✅ Copy assignment");
        System.out.println("    Order& operator=(Order&&);        // ✅ Move assignment (C++11)");
        System.out.println("};");
        System.out.println();
        System.out.println("// Usage:");
        System.out.println("Order o1;");
        System.out.println("Order o2 = o1;              // ✅ Copy constructor");
        System.out.println("Order o3 = std::move(o1);   // ✅ Move constructor (efficient!)");

        System.out.println("\nJava (NO automatic copy/move):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Order {");
        System.out.println("    // NO copy constructor");
        System.out.println("    // NO move constructor");
        System.out.println("    // Must implement clone() manually:");
        System.out.println("    @Override");
        System.out.println("    protected Object clone() throws CloneNotSupportedException {");
        System.out.println("        return super.clone();  // Shallow copy");
        System.out.println("    }");
        System.out.println("}");
        System.out.println();
        System.out.println("// Usage:");
        System.out.println("Order o1 = new Order();");
        System.out.println("Order o2 = o1;              // ❌ NOT a copy (same reference!)");
        System.out.println("Order o3 = (Order) o1.clone(); // ✅ Copy (must cast)");

        // Java demonstration
        try {
            CloneableOrder o1 = new CloneableOrder(100);
            CloneableOrder o2 = o1;  // Same reference
            CloneableOrder o3 = (CloneableOrder) o1.clone();  // Copy

            o2.price = 200;
            o3.price = 300;

            System.out.println("\nJava cloning demonstration:");
            System.out.println("o1.price: " + o1.price);  // 200 (o2 changed it)
            System.out.println("o2.price: " + o2.price);  // 200 (same as o1)
            System.out.println("o3.price: " + o3.price);  // 300 (independent copy)
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        System.out.println("\nKey Difference:");
        System.out.println("• C++:  Copy/move constructors generated automatically");
        System.out.println("        Move semantics for efficiency (C++11)");
        System.out.println("        Deep copy vs shallow copy control");
        System.out.println("• Java: NO copy/move constructors");
        System.out.println("        Must implement Cloneable + clone() manually");
        System.out.println("        NO move semantics (GC handles everything)");
        System.out.println("        Assignment = reference copy (NOT object copy)");
    }

    static class CloneableOrder implements Cloneable {
        int price;

        public CloneableOrder(int price) {
            this.price = price;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();  // Shallow copy
        }
    }

    //=============================================================================
    // 7. DESTRUCTORS vs FINALIZERS/GC
    //=============================================================================

    /**
     * C++: Explicit destructors (~ClassName) called deterministically
     * Java: NO destructors, has finalize() (deprecated) and GC
     */
    static void demonstrateDestructors() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  7. DESTRUCTORS vs FINALIZERS/GC                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ Destructors (Deterministic cleanup):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Connection {");
        System.out.println("    ~Connection() {               // ✅ Destructor");
        System.out.println("        close();                  // Called IMMEDIATELY");
        System.out.println("        release_resources();      // when object destroyed");
        System.out.println("    }");
        System.out.println("};");
        System.out.println();
        System.out.println("// Usage:");
        System.out.println("{");
        System.out.println("    Connection conn;              // Stack object");
        System.out.println("    conn.use();");
        System.out.println("}  // ✅ Destructor called HERE (deterministic!)");
        System.out.println();
        System.out.println("Connection* ptr = new Connection();");
        System.out.println("delete ptr;                       // ✅ Destructor called HERE");

        System.out.println("\nJava GC (Non-deterministic cleanup):");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Connection {");
        System.out.println("    // NO destructor");
        System.out.println("    // finalize() deprecated since Java 9");
        System.out.println("    @Override");
        System.out.println("    protected void finalize() throws Throwable {");
        System.out.println("        // ⚠️ UNPREDICTABLE when/if called!");
        System.out.println("        // ⚠️ Should use try-with-resources instead");
        System.out.println("    }");
        System.out.println("}");
        System.out.println();
        System.out.println("// Modern approach: try-with-resources");
        System.out.println("try (Connection conn = new Connection()) {");
        System.out.println("    conn.use();");
        System.out.println("}  // ✅ close() called HERE (deterministic!)");

        // Java modern approach
        System.out.println("\nJava try-with-resources demonstration:");
        try (AutoCloseableConnection conn = new AutoCloseableConnection()) {
            conn.use();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\nKey Difference:");
        System.out.println("• C++:  Destructor called IMMEDIATELY and DETERMINISTICALLY");
        System.out.println("        Perfect for RAII (Resource Acquisition Is Initialization)");
        System.out.println("        Manual memory management");
        System.out.println("• Java: NO destructors");
        System.out.println("        GC runs UNPREDICTABLY (when memory pressure)");
        System.out.println("        finalize() deprecated (unreliable)");
        System.out.println("        Use try-with-resources for deterministic cleanup");
        System.out.println("        Automatic memory management (GC)");

        System.out.println("\nMemory Management:");
        System.out.println("┌─────────────────┬─────────────────┬─────────────────────┐");
        System.out.println("│ Aspect          │ C++             │ Java                │");
        System.out.println("├─────────────────┼─────────────────┼─────────────────────┤");
        System.out.println("│ Cleanup         │ Destructor      │ GC + finalizer      │");
        System.out.println("│ When            │ Deterministic   │ Non-deterministic   │");
        System.out.println("│ Control         │ Full control    │ No control          │");
        System.out.println("│ Performance     │ Predictable     │ GC pauses           │");
        System.out.println("│ Memory leaks    │ Possible        │ Rare (GC handles)   │");
        System.out.println("│ Resource leaks  │ Manual tracking │ try-with-resources  │");
        System.out.println("└─────────────────┴─────────────────┴─────────────────────┘");
    }

    static class AutoCloseableConnection implements AutoCloseable {
        public void use() {
            System.out.println("Using connection...");
        }

        @Override
        public void close() {
            System.out.println("Connection closed (deterministic cleanup)");
        }
    }

    //=============================================================================
    // 8. DEFAULT CONSTRUCTORS
    //=============================================================================

    /**
     * C++: Default constructor generated if NO constructors defined
     * Java: Default constructor generated if NO constructors defined
     *       (Similar behavior but different semantics)
     */
    static void demonstrateDefaultConstructors() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  8. DEFAULT CONSTRUCTORS                                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ Default Constructor:");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Order {");
        System.out.println("    // If NO constructors defined:");
        System.out.println("    // Order() = default;         // ✅ Generated automatically");
        System.out.println("    //                            // Members UNINITIALIZED!");
        System.out.println("};");
        System.out.println();
        System.out.println("class Order2 {");
        System.out.println("    Order2(int x) { }            // User-defined constructor");
        System.out.println("    // NO default constructor generated!");
        System.out.println("};");
        System.out.println();
        System.out.println("Order o1;                        // ✅ OK (default constructor)");
        System.out.println("Order2 o2;                       // ❌ ERROR! No default constructor");
        System.out.println("Order2 o3(10);                   // ✅ OK (uses defined constructor)");

        System.out.println("\nJava Default Constructor:");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Order {");
        System.out.println("    // If NO constructors defined:");
        System.out.println("    // public Order() { }         // ✅ Generated automatically");
        System.out.println("    //                            // Members initialized to defaults!");
        System.out.println("}");
        System.out.println();
        System.out.println("class Order2 {");
        System.out.println("    Order2(int x) { }            // User-defined constructor");
        System.out.println("    // NO default constructor generated!");
        System.out.println("}");
        System.out.println();
        System.out.println("Order o1 = new Order();          // ✅ OK (default constructor)");
        System.out.println("Order2 o2 = new Order2();        // ❌ ERROR! No default constructor");
        System.out.println("Order2 o3 = new Order2(10);      // ✅ OK (uses defined constructor)");

        // Java demonstration
        DefaultOrder o1 = new DefaultOrder();
        System.out.println("\nJava default initialization: price=" + o1.price);  // 0 (auto-init)

        // CustomOrder o2 = new CustomOrder();  // ❌ Compile error!
        CustomOrder o3 = new CustomOrder(100);
        System.out.println("Custom constructor: price=" + o3.price);

        System.out.println("\nKey Similarity:");
        System.out.println("• Both: Default constructor generated ONLY if no constructors defined");
        System.out.println("• Both: Defining ANY constructor suppresses default constructor");

        System.out.println("\nKey Difference:");
        System.out.println("• C++:  Default constructor leaves members UNINITIALIZED");
        System.out.println("        (undefined behavior if used before assignment)");
        System.out.println("• Java: Default constructor initializes members to DEFAULTS");
        System.out.println("        (0, false, null - always safe)");
    }

    static class DefaultOrder {
        int price;  // Automatically initialized to 0
    }

    static class CustomOrder {
        int price;

        // User-defined constructor (suppresses default constructor)
        public CustomOrder(int price) {
            this.price = price;
        }
    }

    //=============================================================================
    // 9. ACCESS CONTROL
    //=============================================================================

    /**
     * C++: public, protected, private (class-level and inheritance)
     * Java: public, protected, private, package-private (default)
     */
    static void demonstrateAccessControl() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  9. ACCESS CONTROL DIFFERENCES                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ Access Control:");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Order {");
        System.out.println("public:");
        System.out.println("    int id;              // Accessible everywhere");
        System.out.println("protected:");
        System.out.println("    int price;           // Accessible in derived classes");
        System.out.println("private:");
        System.out.println("    int qty;             // Accessible only in this class");
        System.out.println("};");
        System.out.println();
        System.out.println("// Default access: PRIVATE");
        System.out.println("class Order2 {");
        System.out.println("    int x;               // ❌ PRIVATE by default!");
        System.out.println("};");

        System.out.println("\nJava Access Control:");
        System.out.println("───────────────────────────────────────────────────────────");
        System.out.println("class Order {");
        System.out.println("    public int id;              // Accessible everywhere");
        System.out.println("    protected int price;        // Package + subclasses");
        System.out.println("    private int qty;            // Only this class");
        System.out.println("    int status;                 // Package-private (default)");
        System.out.println("}");
        System.out.println();
        System.out.println("// Default access: PACKAGE-PRIVATE");
        System.out.println("class Order2 {");
        System.out.println("    int x;               // ✅ PACKAGE-PRIVATE by default");
        System.out.println("}");

        System.out.println("\nAccess Level Comparison:");
        System.out.println("┌────────────────┬─────────┬──────────┬────────────┬───────────┐");
        System.out.println("│ Modifier       │ Class   │ Package  │ Subclass   │ World     │");
        System.out.println("├────────────────┼─────────┼──────────┼────────────┼───────────┤");
        System.out.println("│ public         │ ✅      │ ✅       │ ✅         │ ✅        │");
        System.out.println("│ protected (C++) │ ✅      │ ❌       │ ✅         │ ❌        │");
        System.out.println("│ protected (Java)│ ✅      │ ✅       │ ✅         │ ❌        │");
        System.out.println("│ package (Java) │ ✅      │ ✅       │ ❌         │ ❌        │");
        System.out.println("│ private        │ ✅      │ ❌       │ ❌         │ ❌        │");
        System.out.println("└────────────────┴─────────┴──────────┴────────────┴───────────┘");

        System.out.println("\nKey Differences:");
        System.out.println("• C++:  Default is PRIVATE");
        System.out.println("        protected = class + derived classes only");
        System.out.println("        No package-level access");
        System.out.println("• Java: Default is PACKAGE-PRIVATE");
        System.out.println("        protected = class + package + derived classes");
        System.out.println("        Has package-level access (default)");
    }

    //=============================================================================
    // 10. SUMMARY COMPARISON TABLE
    //=============================================================================

    static void printSummaryTable() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  10. COMPLETE OOP COMPARISON TABLE                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        System.out.println("┌──────────────────────────┬────────────────────────┬────────────────────────┐");
        System.out.println("│ Feature                  │ C++                    │ Java                   │");
        System.out.println("├──────────────────────────┼────────────────────────┼────────────────────────┤");
        System.out.println("│ Multiple Inheritance     │ ✅ YES (classes)       │ ❌ NO (single class)   │");
        System.out.println("│ Interface Inheritance    │ Abstract classes       │ ✅ Multiple interfaces │");
        System.out.println("│ Virtual by default       │ ❌ NO (explicit)       │ ✅ YES (implicit)      │");
        System.out.println("│ Operator overloading     │ ✅ YES                 │ ❌ NO (except String)  │");
        System.out.println("│ lvalue/rvalue refs       │ ✅ YES (& and &&)      │ ❌ NO (hidden)         │");
        System.out.println("│ Move semantics           │ ✅ YES (C++11)         │ ❌ NO                  │");
        System.out.println("│ Copy constructor         │ ✅ Auto-generated      │ ❌ NO (manual clone)   │");
        System.out.println("│ Destructor               │ ✅ YES (~Class)        │ ❌ NO (GC)             │");
        System.out.println("│ Primitives               │ Value semantics        │ Value semantics        │");
        System.out.println("│ Objects                  │ Value/Reference        │ Reference (ALWAYS)     │");
        System.out.println("│ Stack objects            │ ✅ YES                 │ ❌ NO (primitives only)│");
        System.out.println("│ Heap objects             │ Explicit (new/delete)  │ Automatic (new + GC)   │");
        System.out.println("│ Memory management        │ Manual (RAII)          │ Automatic (GC)         │");
        System.out.println("│ Default constructor      │ If no constructors     │ If no constructors     │");
        System.out.println("│ Default access           │ private                │ package-private        │");
        System.out.println("│ Friend functions         │ ✅ YES                 │ ❌ NO                  │");
        System.out.println("│ Templates/Generics       │ Templates (compile)    │ Generics (runtime)     │");
        System.out.println("│ Multiple dispatch        │ ❌ NO                  │ ❌ NO                  │");
        System.out.println("└──────────────────────────┴────────────────────────┴────────────────────────┘");
    }

    //=============================================================================
    // MAIN
    //=============================================================================

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                ║");
        System.out.println("║       Java vs C++ Object-Oriented Programming Differences      ║");
        System.out.println("║                  Complete Comparison Guide                     ║");
        System.out.println("║                                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        demonstrateMultipleInheritance();
        demonstrateVirtualFunctions();
        demonstrateOperatorOverloading();
        demonstrateReferences();
        demonstrateValueVsReference();
        demonstrateCopyMove();
        demonstrateDestructors();
        demonstrateDefaultConstructors();
        demonstrateAccessControl();
        printSummaryTable();

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAYS                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        System.out.println("C++ Philosophy:");
        System.out.println("• Explicit control (virtual, references, memory)");
        System.out.println("• Value and reference semantics (programmer's choice)");
        System.out.println("• Zero-cost abstractions (pay for what you use)");
        System.out.println("• RAII (Resource Acquisition Is Initialization)");
        System.out.println("• Multiple inheritance (power and complexity)");

        System.out.println("\nJava Philosophy:");
        System.out.println("• Implicit control (all virtual, hidden references, GC)");
        System.out.println("• Reference semantics for objects (no choice)");
        System.out.println("• Simplicity over control (safer, less flexible)");
        System.out.println("• Garbage collection (automatic memory management)");
        System.out.println("• Single inheritance + interfaces (safety over power)");

        System.out.println("\nFor Trading Systems:");
        System.out.println("• C++:  Ultra-low latency, deterministic performance");
        System.out.println("        Full control over memory and performance");
        System.out.println("        Complexity and manual management");
        System.out.println("• Java: Good latency, predictable but with GC pauses");
        System.out.println("        Easier development, automatic memory");
        System.out.println("        Less control, GC overhead");

        System.out.println("\n🚀 Choose based on latency requirements and team expertise!");
    }
}


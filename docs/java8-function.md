## java8-function

Runnable Java 8 functional interfaces examples. Each example contains a `main` method that demonstrates usage.

### How to run

From repository root:

```bash
mvn -pl java8-function -am -q exec:java -Dexec.mainClass="com.asura.function.example.ConsumerExample"
```

Replace the `-Dexec.mainClass` with any of the classes below. Alternatively, run from your IDE.

### Public Classes and Examples

- `com.asura.function.Main`
  - Minimal hello world entry point.

- `com.asura.function.example.ConsumerExample`
  - Demonstrates `Consumer<T>` patterns:
    - Method reference as consumer (`System.out::println`)
    - Chaining with `andThen`
    - Batch processing with `forEach`
    - Exception handling via consumer composition
    - Resource handling pattern with `processFile`
  - Public methods:
    - `static void consumerMethod()` — prints a list with `forEach`.
    - `static void processData(String, Consumer<String>)` — accepts a consumer to process a string.
    - `static void processFile(String, Consumer<BufferedReader>, Consumer<BufferedReader>)` — opens file, applies consumer, then cleanup consumer.

- `com.asura.function.example.SupplierExample`
  - Demonstrates `Supplier<T>`:
    - Lazy computation (`lazyComputation`)
    - Singleton creation via `Supplier`
    - Factory returning `Supplier`
    - Simple cache using `computeIfAbsent`

- `com.asura.function.example.FunctionExample`
  - Demonstrates `Function<T, R>`:
    - Method reference `String::toUpperCase`
    - Currying/partial application (`Function<Integer, Function<Integer, Integer>>`)

- `com.asura.function.example.BiFunctionExample`
  - Demonstrates `BiFunction<T, U, R>` to add two integers.

- `com.asura.function.example.UnaryOperatorExample`
  - Demonstrates `UnaryOperator<T>`:
    - Squaring an integer
    - Updating state of an object via `UnaryOperator<StatefulObject>`

- `com.asura.function.example.BinaryOperatorExample`
  - Demonstrates `BinaryOperator<T>` to compute max of two integers.

- `com.asura.function.example.PredicateExample`
  - Demonstrates `Predicate<T>`:
    - Filtering even numbers with streams
    - Composing conditions with `and` and `negate`
    - Custom `Predicate<String>` for substring contains

### Example Usages

Run `PredicateExample` with Maven Exec plugin:

```bash
mvn -pl java8-function -q exec:java -Dexec.mainClass="com.asura.function.example.PredicateExample"
```

Use `ConsumerExample.processData` in other code:

```java
Consumer<String> logger = s -> System.out.println("Processed: " + s);
ConsumerExample.processData("payload", logger);
```

Use `SupplierExample.Cache` for memoization:

```java
SupplierExample.Cache<String, String> cache = new SupplierExample.Cache<>();
String value = cache.get("key", () -> heavyCompute("key"));
```


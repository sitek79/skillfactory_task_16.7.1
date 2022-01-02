package test_memory_leak;

import calendar.Calendar;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryLeakExample {
    // Класс, реализующий циклическую коллекцию
    static class CyclicCollection {
        private final List<byte[]> list = new ArrayList<>(10);

        CyclicCollection() {
            for (int i = 0; i < 10; i++) {
                list.add(new byte[1024 * 1024]);
                Calendar calendar = new Calendar();
                calendar.calendarPrint(1,2022);
            }
        }

        Element getElement(int index) {
            // Возвращаем один из десяти элементов, хранящихся
            // в списке. В качестве индекса возьмём
            // остаток от деления на 10. Таким образом внешнему
            // наблюдателю будет казаться, что в коллекции
            // бесконечное количество повторяющихся элементов.
            return new Element(list.get(index % 10));
        }

        // Внутренний класс, хранящий в себе
        // элемент коллекции
        class Element {
            final byte[] data;

            Element(byte[] data) {
                this.data = data;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // установим начало таймируемого кода
        Instant start = Instant.now();

        System.out.println("Параметры запуска JVM.");
        runtimeParameters();

        System.out.print("Started...\n");
        // Список, в котором будем хранить по одному элементу
        // из ста циклических коллекций
        List<CyclicCollection.Element> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            CyclicCollection collection = new CyclicCollection();
            list.add(collection.getElement(i));
            Thread.sleep(500);
        }

        // измеряем время
        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        System.out.println("Прошло времени, мс: " + elapsed);

        System.out.println("Finished.");
    }

    public static void runtimeParameters() {
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        System.out.print(bean.toString());
        List<String> aList = bean.getInputArguments();
        System.out.println("");
        for (int i = 0; i < aList.size(); i++) {
            System.out.println(aList.get(i));
        }
    }
}

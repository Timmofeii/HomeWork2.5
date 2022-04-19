import java.util.Arrays;

public class Main {
    //создать два потока , первый создает массив[10лям] и заполняет его 1 находиться он в майне,
    // второй поток- создать класс с наследование потока и в нем метод деления масива по полам и все осатальное,
    // так же создать мониторы, для поочередного выполнения дейсвий и замерять время выполнения работы
    public static void main(String[] args) throws InterruptedException {
        firstMethod();
        secondMethod();
    }

    private static int size = 10_000_000;
    private static int half = size / 2;

    public static int getSize() {

        return size;
    }

    public static int getHalf() {

        return half;
    }


    public static void firstMethod() {
        float[] arr = new float[getSize()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }


    public static void secondMethod() {
        float[] arr = new float[getSize()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;//создали и заполнили массив единицами
        }

        long startTime2 = System.currentTimeMillis();//запустили счетчик 2
        float[] leftHalf = new float[half];
        float[] rightHalf = new float[half];

        Thread threadHalfRight = new Thread(() -> {
            System.arraycopy(arr, getHalf(), rightHalf, 0, getHalf());
            for (int t = 0; t < rightHalf.length; t++) {//просчитываем  малый right  массив
                rightHalf[t] = (float) (rightHalf[t] * Math.sin(0.2f + t / 5)
                        * Math.cos(0.2f + t / 5) * Math.cos(0.4f + t / 2));
            }
        });
        Thread threadHalfLeft = new Thread(() -> {
            System.arraycopy(arr, 0, leftHalf, 0, getHalf());//копировали в них значения из большого массива
            for (int j = 0; j < leftHalf.length; j++) {//просчитываем  малый left  массив
                leftHalf[j] = (float) (leftHalf[j] * Math.sin(0.2f + j / 5)
                        * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
            }
        });
        threadHalfLeft.start();
        threadHalfRight.start();
        float[] newArr = new float[size];
        System.arraycopy(leftHalf, 0, newArr, 0, half);
        System.arraycopy(rightHalf, 0, newArr, half, half);


        System.out.println("Second thread time: " + (System.currentTimeMillis() - startTime2) + " ms.");
    }
}




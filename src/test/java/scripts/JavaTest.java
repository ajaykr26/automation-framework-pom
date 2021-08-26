package scripts;

public class JavaTest {
    public static void main(String[] args) {
        min(57, 800, 87);
    }

    public static void max(int a, int b, int c) {
        int temp = c>b?c:b;

        int result = temp>a?temp:a;
        System.out.println(result);
    }

    public static void min(int a, int b, int c) {
        int temp = c<b?c:b;

        int result = temp<a?temp:a;
        System.out.println(result);
    }
}

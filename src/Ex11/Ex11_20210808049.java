package Ex11;
import java.util.*;

public class Ex11_20210808049 {

    //Q1
    public static int numOfTriplets(int[] arr, int sum) {
        int count = 0;
        int n = arr.length;

        sortArray(arr);//Array'i sort etmem gerekiyor çözümüm için

        for (int i = 0; i < n - 2; i++) {
            //Bu çözümde matematikteki kombinasyonu kullanmam gerek
            int left = i + 1; //başlangıç indeksimin sağındaki indeksle hesaba başlamam gerek
            int right = n - 1; //üçüncü indeks ise en sonda olmalı

            //içerideki if yapısını göre indeksler arasındaki mesafe azalacak.
            //Eğer sağdakinin değeri soldakinden büyük olursa bir tur bitmiş demektir.
            // Bu yüzden ilk değerin indeksi bir artırılmadır
            while (left < right) {
                int tripletSum = arr[i] + arr[left] + arr[right];
                if (tripletSum < sum) {
                    // eğer en sağdaki değer bile sum'dan büyükse aradaki sayılar daha küçük olacağı için
                    // aşadağıdaki işlem yapılıp, soldaki değer bir indeks sağa kaymalıdır
                    count += right - left;
                    left++;
                } else {
                    // Eğer değer büyük geldiyse daha küçük bir değer denemeliyiz
                    //bu yüzden en sağdaki değer bir indeks sola kaydırılmalıdır
                    right--;
                }
            }
        }

        return count;
    }

    //Q2
    public static int kthSmallest(int[] arr, int k){
        sortArray(arr);

        return arr[k-1];
    }
    public static void sortArray(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    //Q3
    public static String subSequence(String str){
        char[] charArray = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            charArray[i] = str.charAt(i);
        }
        int lowest = charArray[0];
        String returnStr = "";
        for (int i = 0; i < str.length(); i++){
            if(charArray[i]>lowest){
                if(charArray[0]==lowest)
                    returnStr+=charArray[0];
                returnStr+=charArray[i];
                lowest = charArray[i];
            }
        }
        System.out.println("n");
        return returnStr;
    }

    //Q4
    public static int isSubString(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        int i = 0;
        int j = 0;

        while (i < n && j < m) {
            if (str1.charAt(i) == str2.charAt(j)) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }

        if (j == m)
            return i - j;
        else
            return -1;

    }
    //Q5
    public static void findRepeats(int[] arr, int n) {
        Map<Integer, Integer> counter = new HashMap<>();
        List<Integer> repeats = new ArrayList<>();

        for (int num : arr)
            counter.put(num, counter.getOrDefault(num, 0) + 1);

        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            if (entry.getValue() > n)
                repeats.add(entry.getKey());
        }

        System.out.println("Elements repeating more than " + n + " times:");
        System.out.println(repeats);
    }
}

package Multithreading;
import java.util.Arrays;

class ConcurrentMergeSort extends Thread {
    private int[] data;
    private int start, end;

    public ConcurrentMergeSort(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        if (start < end) {
            int mid = start + (end - start) / 2;

            ConcurrentMergeSort leftTask = new ConcurrentMergeSort(data, start, mid);
            ConcurrentMergeSort rightTask = new ConcurrentMergeSort(data, mid + 1, end);

            leftTask.start();
            rightTask.start();

            try {
                leftTask.join();  
                rightTask.join(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            merge(data, start, mid, end);
        }
    }

    private static void merge(int[] data, int start, int mid, int end) {
        int len1 = mid - start + 1;
        int len2 = end - mid;

        int[] leftPart = new int[len1];
        int[] rightPart = new int[len2];

        System.arraycopy(data, start, leftPart, 0, len1);
        System.arraycopy(data, mid + 1, rightPart, 0, len2);

        int i = 0, j = 0, k = start;

        while (i < len1 && j < len2) {
            if (leftPart[i] <= rightPart[j]) {
                data[k++] = leftPart[i++];
            } else {
                data[k++] = rightPart[j++];
            }
        }

        while (i < len1) {
            data[k++] = leftPart[i++];
        }

        while (j < len2) {
            data[k++] = rightPart[j++];
        }
    }

    public static void main(String[] args) {
        int[] data = {9, 5, 1, 4, 3, 8, 6, 2, 7};

        System.out.println("Original Array: " + Arrays.toString(data));

        ConcurrentMergeSort sorter = new ConcurrentMergeSort(data, 0, data.length - 1);
        sorter.start();

        try {
            sorter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Sorted Array: " + Arrays.toString(data));
    }
}
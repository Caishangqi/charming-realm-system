package com.GUI;


class NotAnIntegerException extends Exception {
    public NotAnIntegerException(String message) {
        super(message);
    }
}


public class ContentMatrixTransform {

    static int[] PerRowOffset = {19, 21, 23};

    public static int GetTransformedSlotIndex(int PlayerArrayIndex, int NumberOfEntries, int NumberOfElementsPerRow) {
        // PlayerArrayIndex = 34    NumberOfEntries = 21    NumberOfElementsPerRow = 7
        // Check mods
        try {
            int mods = NumberOfEntries % NumberOfElementsPerRow;
            if (mods != 0)
                throw new NotAnIntegerException("请确保总共的个数能被行数整除");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int PageIndex = PlayerArrayIndex % NumberOfEntries; // 34%21 = 13
        int AtRowNumber = (int) Math.floor((double) PageIndex / (double) NumberOfElementsPerRow); // = 1
        //System.out.println("AtRowNumber: " + AtRowNumber + "PlayerArrayIndex: " + PlayerArrayIndex + "NumberOfEntries: " + NumberOfEntries + "NumberOfElementsPerRow: " + NumberOfElementsPerRow);
        int RowOffset = PerRowOffset[AtRowNumber]; // 21

        return PageIndex + RowOffset; // 12 + 21 = 33
    }
}

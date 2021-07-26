package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

    @Test
    void generateSerialNumber() {
        //uses assertion to make sure serial number is 10 characters long
        int length = 10;
        InventoryItem item = new InventoryItem("aaa","aaa");
        assertEquals(length,item.serialNumber.length());
    }

}
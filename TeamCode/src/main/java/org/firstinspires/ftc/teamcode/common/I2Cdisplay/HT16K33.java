package org.firstinspires.ftc.teamcode.common.I2Cdisplay;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;

@SuppressWarnings({"WeakerAccess", "unused"})
@I2cDeviceType
@DeviceProperties(name="Adafruit HT16K33 LED Screen", description="7 Segment Display", xmlTag="HT16K33")
public class HT16K33 extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    int testBit = 0x70;
    public void writeByteArr(DeviceNumber deviceNumber, byte[] character) {
        this.deviceClient.setI2cAddress(deviceNumber.address);
        deviceClient.write(new byte[] {
                0x00,
                character[0], character[1], character[2], character[3],
                character[4], character[5], character[6], character[7],
                character[8], character[9], character[10], character[11],
                character[12], character[13], character[14], character[15]
        });
    }

    public void writeCharacter(DeviceNumber deviceNumber, char character) {
        switch (character) {

        }
    }

    public enum AvailableCharacters {
        EMPTY(new int[] {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}),
        FILLED(new int[] {0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF});
        byte[] charCode;
        AvailableCharacters(int[] code) {
            charCode = new byte[] {
                    (byte) code[0], (byte) code[1], (byte) code[2], (byte) code[3],
                    (byte) code[4], (byte) code[5], (byte) code[6], (byte) code[7],
                    (byte) code[8], (byte) code[9], (byte) code[10], (byte) code[11],
                    (byte) code[12], (byte) code[13], (byte) code[14], (byte) code[15],
            };
        }
    }

    public void testWriteChar() {
        byte thing = (byte) 0xFF;
        deviceClient.write(new byte[] {
                0x00,
                thing, thing, thing, thing,
                thing, thing, thing, thing,
                thing, thing, thing, thing,
                thing, thing, thing, thing,
        });
    }

    public void onePointer() {

    }

    public void turnOff() {
        deviceClient.write8(0x80, I2cWaitControl.WRITTEN);
    }
    public void turnOn() {
        deviceClient.write8(0x81, I2cWaitControl.WRITTEN);
    }

    public void init() {
        deviceClient.write8(0x21, I2cWaitControl.WRITTEN); // write8 is swapped(?)
        deviceClient.write8(0xA0, I2cWaitControl.WRITTEN);
        deviceClient.write8(0xEE, I2cWaitControl.WRITTEN);
        deviceClient.write8(0x80, I2cWaitControl.WRITTEN);
    }


    public enum DeviceNumber {
        ONE(0x70),
        TWO(0x72),
        THREE(0x74),
        FOUR(0x76);
        I2cAddr address;
        DeviceNumber(int address) {
            this.address = I2cAddr.create7bit(address);
        }
    }


    // INIT STUFF
    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x70); // I don't think this matters???
    public HT16K33(I2cDeviceSynch i2cDeviceSynch, boolean deviceClientIsOwned) {
        super(i2cDeviceSynch, deviceClientIsOwned);

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }


    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Adafruit;
    }
    

    @Override
    public String getDeviceName() {
        return "Adafruit HT16K33 LED Screen";
    }
}

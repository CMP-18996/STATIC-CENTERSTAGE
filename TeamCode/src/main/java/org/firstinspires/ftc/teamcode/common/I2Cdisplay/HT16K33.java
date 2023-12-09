package org.firstinspires.ftc.teamcode.common.I2Cdisplay;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;

@SuppressWarnings({"WeakerAccess", "unused"})
@I2cDeviceType
@DeviceProperties(name="Adafruit HT16K33 LED Screen", description="7 Segment Display", xmlTag="HT16K33")
public class HT16K33 extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    int testBit = 0x70;
    public void writeCharacter(DeviceNumber deviceNumber, byte[] character) {
        deviceClient.write(deviceNumber.address, character);
    }

    public void init() {
        deviceClient.write(testBit, new byte[]{(byte) 0x21});
        deviceClient.write(testBit, new byte[]{(byte) 0xA0});
        deviceClient.write(testBit, new byte[]{(byte) 0xEE});
        deviceClient.write(testBit, new byte[]{(byte) 0x81});
    }


    public enum DeviceNumber {
        ONE(0x70),
        TWO(0x72),
        THREE(0x74),
        FOUR(0x76);
        int address;
        DeviceNumber(int address) {
            this.address = address;
        }
    }

    protected void writeShort(final DeviceNumber deviceNumber, short value) {
        deviceClient.write(deviceNumber.address, TypeConversion.shortToByteArray(value));
    }


    // INIT STUFF
    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x70);
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

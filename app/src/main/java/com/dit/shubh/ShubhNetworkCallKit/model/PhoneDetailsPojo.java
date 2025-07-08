package com.dit.shubh.ShubhNetworkCallKit.model;

public class PhoneDetailsPojo {
    private String brand;
    private String manufacturer;
    private String model;
    private String id;
    private String serial;
    private String versionRelease;
    private int sdkInt;
    private String device;
    private String hardware;
    private String host;
    private String product;
    private String user;
    private String type;
    private String fingerprint;
    private String board;
    private String bootloader;
    private String display;

    // Getters and Setters
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSerial() { return serial; }
    public void setSerial(String serial) { this.serial = serial; }

    public String getVersionRelease() { return versionRelease; }
    public void setVersionRelease(String versionRelease) { this.versionRelease = versionRelease; }

    public int getSdkInt() { return sdkInt; }
    public void setSdkInt(int sdkInt) { this.sdkInt = sdkInt; }

    public String getDevice() { return device; }
    public void setDevice(String device) { this.device = device; }

    public String getHardware() { return hardware; }
    public void setHardware(String hardware) { this.hardware = hardware; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getFingerprint() { return fingerprint; }
    public void setFingerprint(String fingerprint) { this.fingerprint = fingerprint; }

    public String getBoard() { return board; }
    public void setBoard(String board) { this.board = board; }

    public String getBootloader() { return bootloader; }
    public void setBootloader(String bootloader) { this.bootloader = bootloader; }

    public String getDisplay() { return display; }
    public void setDisplay(String display) { this.display = display; }

    @Override
    public String toString() {
        return  "📱 Your Phone Details 📋\n" +
                "• Brand         : " + brand + "\n" +
                "• Manufacturer  : " + manufacturer + "\n" +
                "• Model         : " + model + "\n" +
                "• ID            : " + id + "\n" +
                "• Serial        : " + serial + "\n" +
                "• Android Ver   : " + versionRelease + "\n" +
                "• SDK Int       : " + sdkInt + "\n" +
                "• Device        : " + device + "\n" +
                "• Hardware      : " + hardware + "\n" +
                "• Host          : " + host + "\n" +
                "• Product       : " + product + "\n" +
                "• User          : " + user + "\n" +
                "• Type          : " + type + "\n" +
                "• Fingerprint   : " + fingerprint + "\n" +
                "• Board         : " + board + "\n" +
                "• Bootloader    : " + bootloader + "\n" +
                "• Display       : " + display + "\n";
    }


}


package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.*;
import static com.friendly.aqa.pageobject.FileManagementPage.Left.ADD;

@Listeners(UniversalVideoListener.class)
public class FileManagementTests extends BaseTestCase {

    @Test
    public void fileMng_001() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, DELETE)
                .assertButtonsAreEnabled(true, REFRESH, DELETE_BACKUPS);
    }

    @Test
    public void fileMng_002() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .assertMainPageIsDisplayed()
                .bottomMenu(REFRESH)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void fileMng_003() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_image_tr181.img")
                .leftMenu(ADD)
                .selectManufacturer()
                .selectModel()
                .selectFileType("Firmware Image")
                .inputVersion()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    @Test
    public void fileMng_004() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_image_tr181.img")
                .leftMenu(ADD)
                .selectManufacturer()
                .selectModel()
                .selectFileType("Firmware Image")
                .inputVersion()
                .newest()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    @Test
    public void fileMng_005() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_vendor_config_tr181.cfg")
                .leftMenu(ADD)
                .selectManufacturer()
                .selectModel()
                .selectFileType("Vendor Configuration File")
                .inputVersion()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    @Test
    public void fileMng_006() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_vendor_config_tr181.cfg")
                .leftMenu(ADD)
                .selectManufacturer()
                .selectModel()
                .selectFileType("Vendor Configuration File")
                .inputVersion()
                .newest()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    @Test
    public void fileMng_007() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_lwm2m_PSK_credentials.cfg")
                .leftMenu(ADD)
                .selectManufacturer("lwm2m")
                .selectModel("lwm2m")
                .selectFileType("LWM2M PSK Credentials")
                .inputVersion()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    @Test
    public void fileMng_008() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_lwm2m_PSK_credentials.cfg")
                .leftMenu(ADD)
                .selectManufacturer("lwm2m")
                .selectModel("lwm2m")
                .selectFileType("LWM2M PSK Credentials")
                .inputVersion()
                .newest()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    @Test
    public void fileMng_009() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_lwm2m_resource_definition.cfg")
                .leftMenu(ADD)
                .selectManufacturer("lwm2m")
                .selectModel("lwm2m")
                .selectFileType("LWM2M Resource Definition")
                .inputVersion()
                .newest()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    @Test
    public void fileMng_010() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_deployment_file.dpf")
                .leftMenu(ADD)
                .selectManufacturer()
                .selectModel()
                .selectFileType("Deployment File")
                .assertNewestCbIsDisabled()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    @Test
    public void fileMng_011() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_tone_file.ton")
                .leftMenu(ADD)
                .selectManufacturer()
                .selectModel()
                .selectFileType("Tone File")
                .assertNewestCbIsDisabled()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    //012 skipped due to duplicated with 009

    @Test
    public void fileMng_013() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteIfExists("fake_ringer_file.rin")
                .leftMenu(ADD)
                .selectManufacturer()
                .selectModel()
                .selectFileType("Ringer File")
                .assertNewestCbIsDisabled()
                .selectFile()
                .bottomMenu(SAVE_FILE)
                .okButtonPopUp()
                .validateFilePresence();
    }

    //014 skipped due to duplicated with 009

    @Test
    public void fileMng_015() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .validateFiltering("Manufacturer name");
    }

    @Test
    public void fileMng_016() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .validateFiltering("Model name");
    }

    @Test
    public void fileMng_017() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .validateFiltering("File type");
    }

    @Test
    public void fileMng_018() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .selectFileType("Deployment File TR069")
                .saveTableSize()
                .resetView()
                .assertViewIsReset();
    }

    @Test
    public void fileMng_019() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .deleteAndValidateAbsence();
    }

    @Test
    public void fileMng_020() {
        fmPage
                .topMenu(FILE_MANAGEMENT)
                .bottomMenu(DELETE_BACKUPS)
                .okButtonPopUp()
                .assertBackupsAreDeleted();
    }
}

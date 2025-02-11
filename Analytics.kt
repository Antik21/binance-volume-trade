package com.clipnow.docusketch.camera_settings

import androidx.core.os.bundleOf
import com.clipnow.docusketch.analytic.AnalyticsEventParams.KEY_NAME
import com.clipnow.docusketch.analytic.AnalyticsEventParams.KEY_PLACE
import com.clipnow.docusketch.analytic.AnalyticsEventParams.KEY_RESULT
import com.clipnow.docusketch.analytic.AnalyticsEventParams.KEY_SCREEN
import com.clipnow.docusketch.analytic.AnalyticsEventParams.KEY_TYPE
import com.clipnow.docusketch.analytic.AnalyticsTracker
import com.clipnow.docusketch.analytic.extension.toAnalyticsSuccessFailure
import javax.inject.Inject

internal class CameraSettingsAnalytics @Inject constructor(
    private val tracker: AnalyticsTracker,
) {

    private companion object {
        const val EVENT_CAMERA_CONNECTED = "camera_connected"
        const val EVENT_CAMERA_CHANGED = "camera_changed"
        const val EVENT_TRY_AGAIN_PRESSED = "camera_try_again_pressed"
        const val EVENT_OPEN_SETTINGS_PRESSED = "open_settings_pressed"
        const val EVENT_CAMERA_FIRMWARE_OPENED = "camera_firmware_opened"
        const val EVENT_TUTORIAL_OPENED = "tutorial_opened"
        const val EVENT_STORAGE_FULL_SHOWED = "camera_storage_is_full_showed"
        const val UNAUTHORIZED_CAMERA_ERROR = "unauthorized_camera_error"
        const val EVENT_NO_SD_CARD_ERROR = "no_sd_card_error"

        const val PROP_MY_CAMERA = "my_camera"

        const val KEY_DURATION = "duration"
        const val KEY_FAIL_REASON = "fail_reason"

        const val VALUE_RICOH = "RICOH"
        const val VALUE_MI = "MI"
        const val VALUE_INSTA = "INSTA360"
        const val VALUE_CONNECTION_FAIL = "connection fail"
        const val VALUE_CAMERA_CHANGE = "camera change"
        const val VALUE_FIRMWARE_TUTORIAL_OPENED = "camera_firmware_tutorial_opened"
        const val VALUE_CAMERA = "camera"
        const val VALUE_SCREEN_NAME_FOR_UNAUTHORIZED_CAMERA_ERROR_EVENT = "camera_settings_screen"
    }

    fun cameraConnected(success: Boolean, durationSec: Int) {
        tracker.trackEvent(
            EVENT_CAMERA_CONNECTED, bundleOf(
                KEY_RESULT to success.toAnalyticsSuccessFailure(),
                KEY_DURATION to durationSec
            )
        )
    }

    fun cameraChanged(camera: CameraType) {
        val name = when (camera) {
            CameraType.Ricoh -> VALUE_RICOH
            CameraType.Mi -> VALUE_MI
            CameraType.Insta -> VALUE_INSTA
        }
        tracker.trackEvent(
            EVENT_CAMERA_CHANGED, bundleOf(
                KEY_NAME to name
            )
        )
        tracker.setUserProperty(PROP_MY_CAMERA, name)
    }

    fun tryAgainClicked() {
        tracker.trackEvent(EVENT_TRY_AGAIN_PRESSED)
    }

    fun wifiSettingsDisconnectedClicked() {
        tracker.trackEvent(
            EVENT_OPEN_SETTINGS_PRESSED, bundleOf(
                KEY_PLACE to VALUE_CONNECTION_FAIL
            )
        )
    }

    fun wifiSettingsChangeClicked() {
        tracker.trackEvent(
            EVENT_OPEN_SETTINGS_PRESSED, bundleOf(
                KEY_PLACE to VALUE_CAMERA_CHANGE
            )
        )
    }

    fun cameraFirmwareOpened(place: String) {
        tracker.trackEvent(
            EVENT_CAMERA_FIRMWARE_OPENED, bundleOf(
                KEY_PLACE to place
            )
        )
    }

    fun firmwareTutorialOpened() {
        tracker.trackEvent(
            EVENT_TUTORIAL_OPENED, bundleOf(
                KEY_NAME to VALUE_FIRMWARE_TUTORIAL_OPENED
            )
        )
    }

    fun cameraStorageIsFullShowed() {
        tracker.trackEvent(
            EVENT_STORAGE_FULL_SHOWED, bundleOf(
                KEY_TYPE to VALUE_CAMERA
            )
        )
    }

    fun unauthorizedCameraError() {
        tracker.trackEvent(
            UNAUTHORIZED_CAMERA_ERROR, bundleOf(
                KEY_SCREEN to VALUE_SCREEN_NAME_FOR_UNAUTHORIZED_CAMERA_ERROR_EVENT
            )
        )
    }

    fun cameraNoSDCard() {
        tracker.trackEvent(
            EVENT_NO_SD_CARD_ERROR, bundleOf(
                KEY_SCREEN to VALUE_SCREEN_NAME_FOR_UNAUTHORIZED_CAMERA_ERROR_EVENT
            )
        )
    }
}

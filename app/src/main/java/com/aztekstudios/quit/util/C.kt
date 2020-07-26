package com.aztekstudios.quit.util

/**
 * Constants Class
 */
class C {
    companion object {
        const val NOTIFICATION_ID = 0x3f
        const val PINT_ID = 0x1a0f
        const val CHANNEL_ID = "CHANNEL_QUITAPP_NOTIFICATIONS"
        const val UNIQUE_JOB_NAME = "ServiceSchedulerJob"
        const val BROADCAST_DATA_UPDATE = "DataUpdated"
        const val DATA_LAUNCHER = "OnLauncher"
        const val SIGN_IN_CODE = 124

        // Badges
        const val BADGE_1 = "NotSoGoodMaybe \uD83D\uDE11"
        const val BADGE_2 = "ISeeSomething \uD83E\uDD2A"
        const val BADGE_3 = "MovingForward \uD83D\uDE2E"
        const val BADGE_4 = "Improving \uD83D\uDE09"
        const val BADGE_5 = "Awesome \uD83E\uDD29"
        const val BADGE_TOP = "Superb \uD83E\uDD29"

        // Constant stages
        const val BADGE_ASTAGE1 = 10
        const val BADGE_ASTAGE2 = 20
        const val BADGE_ASTAGE3 = 25
        const val BADGE_ASTAGE4 = 30
        const val BADGE_ASTAGE5 = 40
        const val BADGE_BSTAGE1 = 0.15
        const val BADGE_BSTAGE2 = 0.20
        const val BADGE_BSTAGE3 = 0.30
        const val BADGE_BSTAGE4 = 1.0
        const val BADGE_BSTAGE5 = 2

        // Constants for shared preferences
        const val PREF_GOAL_USAGE = "UsageGoals"
        const val PREF_GOAL_UNLOCKS = "UnlockGoals"
        const val PREFS_APP = "ApplicationD"
        const val PREF_LASTSAVED = "LastSaved"
        const val PREF_PROFILE = "UserProfile"
        const val PREF_UNLOCKS = "TimesUnlockedVar"
        const val PREF_USAGE = "TimeUsedVar"
        const val PREF_BADGE = "CurrentBadge"
        const val PREF_TIMEOUT = "15"
        const val PREF_UNLOCKS_MAX = "MaxUnlockedVar"
        const val PREF_TIME_TYPE = "TimeType"
        const val PREF_TIME_TEMP = "TimeTemp"
        const val TIME_TYPE_HRS = "Hours"
        const val TIME_TYPE_MINS = "Minutes"

        // Profile Constants
        const val PROFILE_NAME = "UserName"
        const val PROFILE_MAIL = "UserEMail"
        const val PROFILE_AGE = "UserDetails"
        const val PROFILE_PROFESSION = "UserProfession"

        //const val PROFILE_TYPE = "UserType"
        const val PROFILE_USER_IMAGE = "UserProfileImage"
        const val PROFILE_USER_UID = "UserUniqueID"
        const val PROFILE_SIGNUP_YEAR = "SignupYear"
        const val PROFILE_SIGNUP_AGE = "SignupAge" // WE need age, don't we?
        const val PROFILE_USER_GENDER = "UserGender"

    }
}

/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "C.kt" is a part of the project "Quit"                                                      *
 *                                                                                                          *
 *     Quit is free software: you can redistribute it and/or modify                                         *
 *     it under the terms of the GNU General Public License as published by                                 *
 *     the Free Software Foundation, either version 3 of the License, or                                    *
 *     (at your option) any later version.                                                                  *
 *                                                                                                          *
 *     Project Quit is distributed in the hope that it will be useful,                                      *
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of                                       *
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                        *
 *     GNU General Public License for more details.                                                         *
 *                                                                                                          *
 *     You should have received a copy of the GNU General Public License                                    *
 *     along with Project Quit.  If not, see <https://www.gnu.org/licenses/>.                               *
 *                                                                                                          *
 ************************************************************************************************************/

package com.aztekstudios.quit.util

/**
 * Constants Class
 */
class C {
    /**
     * This object contains the constants used throughout the program
     */
    companion object {
        const val NOTIFICATION_ID = 0x3f    // Notification ID constant
        const val PINT_ID = 0x1a0f          // Pending Intent ID constant
        const val CHANNEL_ID = "CHANNEL_QUITAPP_NOTIFICATIONS"
        const val UNIQUE_JOB_NAME = "ServiceSchedulerJob"
        //const val BROADCAST_DATA_UPDATE = "DataUpdated" // Future implementation
        const val SIGN_IN_CODE = 124        // Constant to call sign-in intent

        // Badges
        const val BADGE_1 = "NotSoGoodMaybe \uD83D\uDE11"       // This badge that represents lowest performance
        const val BADGE_2 = "ISeeSomething \uD83E\uDD2A"        // This badge that represents sightly good performance
        const val BADGE_3 = "MovingForward \uD83D\uDE2E"        // This badge that represents slightly improving performance
        const val BADGE_4 = "Improving \uD83D\uDE09"            // This badge that represents better performance
        const val BADGE_5 = "Awesome \uD83E\uDD29"              // This badge that represents high performance
        const val BADGE_TOP = "Superb \uD83E\uDD29"             // This badge that represents highest performance

        // Constants stages for evaluating performance

        // Unlock stages
        const val BADGE_STAGE_A1 = 10                            // Stage 1 representing bare minimum unlocks
        const val BADGE_STAGE_A2 = 20                            // Stage 2 representing a decent minimum unlocks
        const val BADGE_STAGE_A3 = 25                            // Stage 3 representing improving unlocks
        const val BADGE_STAGE_A4 = 30                            // Stage 4 representing high performance unlocks
        const val BADGE_STAGE_A5 = 40                            // Stage 5 representing best performance unlocks
        // Time stage
        const val BADGE_STAGE_B1 = 0.15                          // Stage 1 representing bare minimum time
        const val BADGE_STAGE_B2 = 0.20                          // Stage 2 representing a decent minimum time
        const val BADGE_STAGE_B3 = 0.30                          // Stage 3 representing improving time
        const val BADGE_STAGE_B4 = 1.0                           // Stage 4 representing high performance time
        const val BADGE_STAGE_B5 = 2                             // Stage 5 representing best performance time

        // Constants for shared preferences - Self explanatory
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

        // Profile Constants - Self explanatory
        const val PROFILE_NAME = "UserName"
        const val PROFILE_MAIL = "UserEMail"
        const val PROFILE_AGE = "UserDetails"
        const val PROFILE_PROFESSION = "UserProfession"
        const val PROFILE_USER_IMAGE = "UserProfileImage"
        const val PROFILE_USER_UID = "UserUniqueID"
        const val PROFILE_SIGNUP_YEAR = "SignupYear"
        const val PROFILE_SIGNUP_AGE = "SignupAge"
        const val PROFILE_USER_GENDER = "UserGender"
        //const val PROFILE_TYPE = "UserType"

    }
}

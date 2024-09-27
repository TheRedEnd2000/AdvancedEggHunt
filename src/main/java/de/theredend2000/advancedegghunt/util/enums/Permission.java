package de.theredend2000.advancedegghunt.util.enums;

import de.theredend2000.advancedegghunt.Main;

import java.text.MessageFormat;
import java.util.logging.Level;

public enum Permission {
    BreakEgg,
    PlaceEgg,
    IgnoreCooldown,
    ChangeCollections,
    CreateCollection,
    OpenRewards,
    FindEggs;
    @Override
    public String toString() {
        return "AdvancedEggHunt." + this.name();
    }

    public static Permission getEnum(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, MessageFormat.format("Failed to convert {0} to Enum.", value), e);
            return null;
        }
    }
    public enum Command {
        place,
        eggImport,
        list,
        show,
        reload,
        help,
        settings,
        collection,
        progress,
        commands,
        leaderboard,
        hint,
        reset,
        exportPreset;

        @Override
        public String toString() {
            return "AdvancedEggHunt.Command." + this.name();
        }

        public static Command getEnum(String value) {
            try {
                return valueOf(value);
            } catch (IllegalArgumentException e) {
                Main.getInstance().getLogger().log(Level.SEVERE, MessageFormat.format("Failed to convert {0} to Enum.", value), e);
                return null;
            }
        }
    }
}

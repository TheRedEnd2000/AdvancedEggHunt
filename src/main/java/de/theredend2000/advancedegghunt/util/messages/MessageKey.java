package de.theredend2000.advancedegghunt.util.messages;

public enum MessageKey {
    PERMISSION_ERROR("no-permission-error"),
    COMMAND_NOT_FOUND("command-not-found"),
    ONLY_PLAYER("only-player"),
    RELOAD_CONFIG("reload-config"),
    PLAYER_NOT_FOUND("player-not-found"),
    NOT_NUMBER("not-number"),

    EGG_PLACED("egg-placed"),
    EGG_BROKEN("egg-broken"),
    TELEPORT_TO_EGG("teleport-to-egg"),

    EGG_FOUND("egg-found"),
    EGG_ALREADY_FOUND("egg-already-found"),
    EGG_ALREADY_FOUND_BY_PLAYER("egg-already-found-by-player"),
    ALL_EGGS_FOUND("all-eggs-found"),
    NO_EGGS("no-eggs"),

    EGG_HINT("egg-hint"),
    HINT_COOLDOWN("hint-cooldown"),
    CLICKED_SAME("clicked-same"),
    EGG_HINT_TIMEOUT("hint-timeout"),
    EGG_HINT_CANCELLED("hint-cancelled"),

    EGG_NOT_ACCESSED("egg-not-accessed"),

    ONLY_IN_PLACEMODE("only-in-placemode"),
    ENTER_PLACEMODE("enter-placemode"),
    LEAVE_PLACEMODE("leave-placemode"),

    FIRST_PAGE("first-page"),
    LAST_PAGE("last-page"),
    WAIT_REFRESH("wait-refresh"),
    BLOCK_LISTED("block-listed"),

    EGG_NEARBY("egg-nearby"),

    SOUND_VOLUME("sound-volume"),
    EGG_VISIBLE("egg-visible"),
    EGG_SHOW_WARNING("egg-show-warning"),
    ARMORSTAND_GLOW("armorstand-glow"),
    EGG_RADIUS("egg-radius"),

    COMMAND_DELETE("command-delete"),
    NEW_COMMAND("new-command"),
    COMMAND_CANCEL("command-cancel"),
    COMMAND_ADD("command-add"),
    COMMAND_EXPIRED("command-expired"),
    ONE_COMMAND("one-command"),
    COMMAND_BLACKLISTED("command-blacklisted"),

    FOUNDEGGS_RESET("foundeggs-reset"),
    FOUNDEGGS_PLAYER_RESET("foundeggs-player-reset"),
    FOUNDEGGS_PLAYER_RESET_COLLECTION("foundeggs-player-reset-collection"),

    ACTIVATE_REQUIREMENTS("activate-requirements"),
    DEACTIVATE_REQUIREMENTS("deactivate-requirements"),

    COLLECTION_DISABLED("collection-disabled"),
    COLLECTION_SELECTION("collection-selected"),
    COLLECTION_DELETED("collection-deleted"),
    CHANCED_CHANCE("chanced-chance"),
    RARITY_MESSAGE("rarity-message"),

    EGGIMPORT_HAND("eggimport-hand"),
    EGGIMPORT_FAILED_PROFILE("eggimport-failed-profile"),
    EGGIMPORT_SUCCESS("eggimport-success"),

    PRESET_ALREADY_EXISTS("preset-already-exists"),
    PRESET_SAVED("preset-saved"),
    PRESET_FAILED_COMMANDS("preset-failed-commands"),
    PRESET_LOADED("preset-loaded"),
    PRESET_DELETE("preset-delete"),
    PRESET_DEFAULT("preset-default"),
    PRESET_NOT_DELETE_DEFAULT("preset-not-delete-default"),

    SETTING_COMMANDFEEDBACK("setting-commandfeedback"),

    COMMANDS_OUTDATED("commands-outdated"),

    HELP_MESSAGE("help-message"),

    EGG_DATA_CONVERTED_SUCCESS("egg-data-converted-success"),
    EGG_DATA_CONVERTED_BROADCAST("egg-data-converted-broadcast"),
    EGG_DATA_CONVERTED_FAILED("egg-data-converted-failed"),
    EGG_DATA_CONVERT_START("egg-data-convert-start"),
    EGG_DATA_CONVERT_BROADCAST("egg-data-convert-broadcast"),
    EGG_DATA_CONVERT_OP_MESSAGE("egg-data-convert-op-message"),
    EGG_DATA_CONVERT_LOCATIONS("egg-data-convert-locations"),
    EGG_DATA_CONVERT_PLAYER("egg-data-convert-player"),
    EGG_DATA_CONVERT_DONE("egg-data-convert-done"),
    EGG_DATA_CONVERT_SUCCESS("egg-data-convert-success"),
    EGG_DATA_CONVERT_DELETE_FAIL("egg-data-convert-delete-fail"),
    EGG_DATA_CONVERT_DELETE_ERROR("egg-data-convert-delete-error"),
    EGG_DATA_CONVERT_COMPLETE("egg-data-convert-complete"),
    PLACEHOLDERAPI_DETECTED("placeholderapi-detected"),
    PLACEHOLDERAPI_ENABLED("placeholderapi-enabled"),
    INIT_DATA_PLAYERS_LOADED("init-data-players-loaded"),
    INIT_DATA_COLLECTIONS_LOADED("init-data-collections-loaded"),
    MATERIAL_ERROR_CONSOLE("material-error-console"),
    SUCCESSFULLY_CHANGED_BLOCK("successfully-changed-block"),
    REQUIREMENT_SECTION_UNAVAILABLE("requirement-section-unavailable"),
    REQUIREMENTS_ORDER_COMING_SOON("requirements-order-coming-soon"),
    COLLECTION_SELECT_ERROR("collection-select-error"),
    COLLECTION_REFRESH_COOLDOWN("collection-refresh-cooldown"),
    COLLECTION_REFRESH_SUCCESS("collection-refresh-success"),
    COLLECTION_EDIT_NO_PERMISSION("collection-edit-no-permission");

    private final String path;

    MessageKey(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

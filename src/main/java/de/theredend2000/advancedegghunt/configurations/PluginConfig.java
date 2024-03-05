package de.theredend2000.advancedegghunt.configurations;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.particles.XParticle;
import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.configurations.enums.Permission;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Map;

import static java.util.Map.entry;

public class PluginConfig extends Configuration {
    private static volatile PluginConfig instance;
    private final Map<Double, ConfigurationVersion> ConfigUpdate = Map.ofEntries(
            entry(1d, new ConfigurationVersion(Map.ofEntries(), Map.ofEntries())),
            entry(2d, new ConfigurationVersion(Map.ofEntries(), Map.ofEntries()))
    );

    private PluginConfig(JavaPlugin plugin) {
        super(plugin, "config.yml");
    }

    public static PluginConfig getInstance(JavaPlugin plugin) {
        if (instance == null) {
            synchronized (PluginConfig.class) {
                if (instance == null) {
                    instance = new PluginConfig(plugin);
                }
            }
        }
        return instance;
    }

    public void saveData() {
        saveConfig();
    }

    public double getConfigVersion() {
        return getConfig().getDouble("config-version");
    }
	public void setConfigVersion(double ConfigVersion) {
		getConfig().set("config-version", ConfigVersion);
	}

    public String getLanguage() {
        return getConfig().getString("messages-lang");
    }
    public void setLanguage(String language) {
        getConfig().set("messages-lang", language);
    }

    public String getPrefix() {
        return getConfig().getString("prefix");
    }
	public void setPrefix(String Prefix) {
		getConfig().set("prefix", Prefix);
	}

    public Boolean getPermissionEnabled(Permission permission) {
        return getConfig().getBoolean(MessageFormat.format("Permissions.{0}.use", permission.toString()));
    }
	public void setPermissionEnabled(Permission permission, String Permission) {
		getConfig().set(MessageFormat.format("Permissions.{0}.use", permission.toString()), Permission);
	}

    public String getPermission(Permission permission) {
        return getConfig().getString(MessageFormat.format("Permissions.{0}.permission", permission.toString()));
    }
    public void setPermission(Permission permission, String Permission) {
        getConfig().set(MessageFormat.format("Permissions.{0}.permission", permission.toString()), Permission);
    }

    //region Settings
    public Integer getSoundVolume() {
        return getConfig().getInt("Settings.SoundVolume");
    }
	public void setSoundVolume(Integer SoundVolume) {
		getConfig().set("Settings.SoundVolume", SoundVolume);
	}

    public boolean getUpdater() {
        return getConfig().getBoolean("Settings.Updater");
    }
	public void setUpdater(boolean Updater) {
		getConfig().set("Settings.Updater", Updater);
	}

    public boolean getPlayerFoundOneEggRewards() {
        return getConfig().getBoolean("Settings.PlayerFoundOneEggRewards");
    }
	public void setPlayerFoundOneEggRewards(boolean PlayerFoundOneEggRewards) {
		getConfig().set("Settings.PlayerFoundOneEggRewards", PlayerFoundOneEggRewards);
	}

    public boolean getPlayerFoundAllEggsReward() {
        return getConfig().getBoolean("Settings.PlayerFoundAllEggsReward");
    }
	public void setPlayerFoundAllEggsReward(boolean PlayerFoundAllEggsReward) {
		getConfig().set("Settings.PlayerFoundAllEggsReward", PlayerFoundAllEggsReward);
	}

    public boolean getDisableCommandFeedback() {
        return getConfig().getBoolean("Settings.DisableCommandFeedback");
    }
	public void setDisableCommandFeedback(boolean DisableCommandFeedback) {
		getConfig().set("Settings.DisableCommandFeedback", DisableCommandFeedback);
	}

    public boolean getShowCoordinatesWhenEggFoundInProgressInventory() {
        return getConfig().getBoolean("Settings.ShowCoordinatesWhenEggFoundInProgressInventory");
    }
	public void setShowCoordinatesWhenEggFoundInProgressInventory(boolean ShowCoordinatesWhenEggFoundInProgressInventory) {
		getConfig().set("Settings.ShowCoordinatesWhenEggFoundInProgressInventory", ShowCoordinatesWhenEggFoundInProgressInventory);
	}

    public boolean getShowFireworkAfterEggFound() {
        return getConfig().getBoolean("Settings.ShowFireworkAfterEggFound");
    }
	public void setShowFireworkAfterEggFound(boolean ShowFireworkAfterEggFound) {
		getConfig().set("Settings.ShowFireworkAfterEggFound", ShowFireworkAfterEggFound);
	}

    public Integer getArmorstandGlow() {
        return getConfig().getInt("Settings.ArmorstandGlow");
    }
	public void setArmorstandGlow(Integer ArmorstandGlow) {
		getConfig().set("Settings.ArmorstandGlow", ArmorstandGlow);
	}

    public Integer getShowEggsNearbyMessageRadius() {
        return getConfig().getInt("Settings.ShowEggsNearbyMessageRadius");
    }
	public void setShowEggsNearbyMessageRadius(Integer ShowEggsNearbyMessageRadius) {
		getConfig().set("Settings.ShowEggsNearbyMessageRadius", ShowEggsNearbyMessageRadius);
	}

    public XMaterial getRewardInventoryMaterial() {
        return Main.getMaterial(getConfig().getString("Settings.RewardInventoryMaterial"));
    }

    public boolean getPluginPrefixEnabled() {
        return getConfig().getBoolean("Settings.PluginPrefixEnabled");
    }
	public void setPluginPrefixEnabled(boolean PluginPrefixEnabled) {
		getConfig().set("Settings.PluginPrefixEnabled", PluginPrefixEnabled);
	}

    public boolean getLeftClickEgg() {
        return getConfig().getBoolean("Settings.LeftClickEgg");
    }
	public void setLeftClickEgg(boolean LeftClickEgg) {
		getConfig().set("Settings.LeftClickEgg", LeftClickEgg);
	}

    public Integer getRightClickEgg() {
        return getConfig().getInt("Settings.RightClickEgg");
    }
	public void setRightClickEgg(Integer RightClickEgg) {
		getConfig().set("Settings.RightClickEgg", RightClickEgg);
	}

    public Integer getHintCount() {
        return getConfig().getInt("Settings.HintCount");
    }
	public void setHintCount(Integer HintCount) {
		getConfig().set("Settings.HintCount", HintCount);
	}

    public Integer getHintCooldownSeconds() {
        return getConfig().getInt("Settings.HintCooldownSeconds");
    }
	public void setHintCooldownSeconds(Integer HintCooldownSeconds) {
		getConfig().set("Settings.HintCooldownSeconds", HintCooldownSeconds);
	}

    public Integer getHintUpdateTime() {
        return getConfig().getInt("Settings.HintUpdateTime");
    }
	public void setHintUpdateTime(Integer HintUpdateTime) {
		getConfig().set("Settings.HintUpdateTime", HintUpdateTime);
	}

    //endregion

    //region Sounds
    public XMaterial getPlayerFindEggSound() {
        return Main.getMaterial(getConfig().getString("Settings.PlayerFindEggSound"));
    }
	public void setPlayerFindEggSound(XMaterial PlayerFindEggSound) {
		getConfig().set("Settings.PlayerFindEggSound", PlayerFindEggSound.toString());
	}

    public XMaterial getEggAlreadyFoundSound() {
        return Main.getMaterial(getConfig().getString("Settings.EggAlreadyFoundSound"));
    }
	public void setEggAlreadyFoundSound(XMaterial EggAlreadyFoundSound) {
		getConfig().set("Settings.EggAlreadyFoundSound", EggAlreadyFoundSound.toString());
	}

    public XMaterial getAllEggsFoundSound() {
        return Main.getMaterial(getConfig().getString("Settings.AllEggsFoundSound"));
    }
	public void setAllEggsFoundSound(XMaterial AllEggsFoundSound) {
		getConfig().set("Settings.AllEggsFoundSound", AllEggsFoundSound.toString());
	}

    public XMaterial getEggBreakSound() {
        return Main.getMaterial(getConfig().getString("Settings.EggBreakSound"));
    }
	public void setEggBreakSound(XMaterial EggBreakSound) {
		getConfig().set("Settings.EggBreakSound", EggBreakSound.toString());
	}

    public XMaterial getEggPlaceSound() {
        return Main.getMaterial(getConfig().getString("Settings.EggPlaceSound"));
    }
	public void setEggPlaceSound(XMaterial EggPlaceSound) {
		getConfig().set("Settings.EggPlaceSound", EggPlaceSound.toString());
	}

    public XMaterial getErrorSound() {
        return Main.getMaterial(getConfig().getString("Settings.ErrorSound"));
    }
	public void setErrorSound(XMaterial ErrorSound) {
		getConfig().set("Settings.ErrorSound", ErrorSound.toString());
	}

    public XMaterial getInventoryClickSuccess() {
        return Main.getMaterial(getConfig().getString("Settings.InventoryClickSuccess"));
    }
	public void setInventoryClickSuccess(XMaterial InventoryClickSuccess) {
		getConfig().set("Settings.InventoryClickSuccess", InventoryClickSuccess.toString());
	}

    public XMaterial getInventoryClickFailed() {
        return Main.getMaterial(getConfig().getString("Settings.InventoryClickFailed"));
    }
	public void setInventoryClickFailed(XMaterial InventoryClickFailed) {
		getConfig().set("Settings.InventoryClickFailed", InventoryClickFailed.toString());
	}

    //endregion

    //region Particle
    public boolean getParticleEnabled() {
        return getConfig().getBoolean("Particle.enabled");
    }
	public void setParticleEnabled(boolean ParticleEnabled) {
		getConfig().set("Particle.enabled", ParticleEnabled);
	}

    @Nullable
    public Particle getEggFoundParticle() {
        return XParticle.getParticle(getConfig().getString("Particle.type.EggFound", "CRIT"));
    }
	public void setEggFoundParticle(Particle EggFoundParticle) {
		getConfig().set("Particle.type.EggFound", EggFoundParticle.toString());
	}

    @Nullable
    public Particle getEggNotFoundParticle() {
        return XParticle.getParticle(getConfig().getString("Particle.type.EggNotFound", "VILLAGER_HAPPY"));
    }
	public void setEggNotFoundParticle(Particle EggNotFoundParticle) {
		getConfig().set("Particle.type.EggNotFound", EggNotFoundParticle.toString());
	}
    //endregion

    //region PlaceholderAPI
    public String getCollection() {
        return getConfig().getString("PlaceholderAPI.collection");
    }
	public void setCollection(String Collection) {
		getConfig().set("PlaceholderAPI.collection", Collection);
	}

    public String getName() {
        return getConfig().getString("PlaceholderAPI.name");
    }
	public void setName(String Name) {
		getConfig().set("PlaceholderAPI.name", Name);
	}

    public String getCount() {
        return getConfig().getString("PlaceholderAPI.count");
    }
	public void setCount(String Count) {
		getConfig().set("PlaceholderAPI.count", Count);
	}
    //endregion

    //region PlaceEggs
    public Integer getPlaceEggTexture(int id) {
        return getConfig().getInt(MessageFormat.format("PlaceEggs.{0}.texture", id));
    }
	public void setPlaceEggTexture(int id, Integer PlaceEggTexture) {
		getConfig().set(MessageFormat.format("PlaceEggs.{0}.texture", id), PlaceEggTexture);
	}
    //endregion
}

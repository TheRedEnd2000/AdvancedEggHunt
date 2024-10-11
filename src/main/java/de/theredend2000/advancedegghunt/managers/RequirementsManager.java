package de.theredend2000.advancedegghunt.managers;

import com.cryptomorin.xseries.XMaterial;
import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.util.DateTimeUtil;
import de.theredend2000.advancedegghunt.util.enums.DeletionTypes;
import de.theredend2000.advancedegghunt.util.enums.Requirements;
import de.theredend2000.advancedegghunt.util.messages.MessageKey;
import de.theredend2000.advancedegghunt.util.messages.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RequirementsManager {

    private Main plugin;
    private MessageManager messageManager;

    public RequirementsManager(){
        this.plugin = Main.getInstance();
        messageManager = plugin.getMessageManager();
    }

    public void getListRequirementsLoreAsync(String collection, Consumer<List<String>> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            List<String> lore = getListRequirementsLore(collection);
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> callback.accept(lore));
        });
    }

    private List<String> getListRequirementsLore(String collection){
        String pre = "Requirements.";
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6§lListed:");
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        if (placedEggs.contains("Requirements.Hours")) {
            List<String> hoursList = new ArrayList<>(placedEggs.getConfigurationSection("Requirements.Hours").getKeys(false));

            if (!hoursList.isEmpty()) {
                hoursList.sort(Comparator.comparingInt(Integer::parseInt));
                int counter = 0;
                lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_HOUR)+":");
                for (String hours : hoursList) {
                    if (placedEggs.getBoolean(pre + ".Hours." + hours)) {
                        if (counter < 3)
                            lore.add("  §7- " + hours);
                        counter++;
                    }
                }
                if(counter == 0) {
                    lore.add("  §cN/A");
                }
                if(counter > 3)
                    lore.add("  §7§o+" + (counter-3) + " "+messageManager.getMessage(MessageKey.REQUIREMENTS_MORE)+"...");
            }
        }else{
            lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_HOUR)+":");
            lore.add("  §cN/A");
        }
        if (placedEggs.contains("Requirements.Date")) {
            ArrayList<String> dateList = new ArrayList<>(placedEggs.getConfigurationSection("Requirements.Date").getKeys(false));

            if (!dateList.isEmpty()) {
                dateList.sort(Comparator.comparingInt(day -> DateTimeUtil.getAllDaysOfYear().indexOf(day)));
                int counter = 0;
                lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_DATE)+":");
                for (String dates : dateList) {
                    if (placedEggs.getBoolean(pre + ".Date." + dates)) {
                        if (counter < 3)
                            lore.add("  §7- " + dates);
                        counter++;
                    }
                }
                if(counter == 0) {
                    lore.add("  §cN/A");
                }
                if(counter > 3)
                    lore.add("  §7§o+" + (counter-3) + " "+messageManager.getMessage(MessageKey.REQUIREMENTS_MORE)+"...");
            }
        } else{
            lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_DATE)+":");
            lore.add("  §cN/A");
        }
        if (placedEggs.contains("Requirements.Weekday")) {
            List<String> weekdayList = new ArrayList<>(placedEggs.getConfigurationSection("Requirements.Weekday").getKeys(false));

            if (!weekdayList.isEmpty()) {
                weekdayList.sort(Comparator.comparingInt(day -> DateTimeUtil.getWeekList().indexOf(day)));
                int counter = 0;
                lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_WEEKDAY)+":");
                for (String weekdays : weekdayList) {
                    if (placedEggs.getBoolean(pre + ".Weekday." + weekdays)) {
                        if (counter < 3)
                            lore.add("  §7- " + weekdays);
                        counter++;
                    }
                }
                if(counter == 0) {
                    lore.add("  §cN/A");
                }
                if(counter > 3)
                    lore.add("  §7§o+" + (counter-3) + " "+messageManager.getMessage(MessageKey.REQUIREMENTS_MORE)+"...");
            }
        }else{
            lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_WEEKDAY)+":");
            lore.add("  §cN/A");
        }
        if (placedEggs.contains("Requirements.Month")) {
            List<String> monthList = new ArrayList<>(placedEggs.getConfigurationSection("Requirements.Month").getKeys(false));

            if (!monthList.isEmpty()) {
                monthList.sort(Comparator.comparingInt(day -> DateTimeUtil.getMonthList().indexOf(day)));
                int counter = 0;
                lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_MONTH)+":");
                for (String month : monthList) {
                    if (placedEggs.getBoolean(pre + ".Month." + month)) {
                        if (counter < 3)
                            lore.add("  §7- " + month);
                        counter++;
                    }
                }
                if(counter == 0) {
                    lore.add("  §cN/A");
                }
                if(counter > 3)
                    lore.add("  §7§o+" + (counter-3) + " "+messageManager.getMessage(MessageKey.REQUIREMENTS_MORE)+"...");
            }
        }else{
            lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_MONTH)+":");
            lore.add("  §cN/A");
        }
        if (placedEggs.contains("Requirements.Year")) {
            List<String> yearList = new ArrayList<>(placedEggs.getConfigurationSection("Requirements.Year").getKeys(false));

            if (!yearList.isEmpty()) {
                yearList.sort(Comparator.comparingInt(Integer::parseInt));
                int counter = 0;
                lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_YEAR)+":");
                for (String year : yearList) {
                    if (placedEggs.getBoolean(pre + ".Year." + year)) {
                        if (counter < 3)
                            lore.add("  §7- " + year);
                        counter++;
                    }
                }
                if(counter == 0) {
                    lore.add("  §cN/A");
                }
                if(counter > 3)
                    lore.add("  §7§o+" + (counter-3) + " "+messageManager.getMessage(MessageKey.REQUIREMENTS_MORE)+"...");
            }
        }else{
            lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_YEAR)+":");
            lore.add("  §cN/A");
        }
        if (placedEggs.contains("Requirements.Season")) {
            List<String> seasonList = new ArrayList<>(placedEggs.getConfigurationSection("Requirements.Season").getKeys(false));

            if (!seasonList.isEmpty()) {
                seasonList.sort(Comparator.comparingInt(day -> DateTimeUtil.getSeasonList().indexOf(day)));
                int counter = 0;
                lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_SEASON)+":");
                for (String season : seasonList) {
                    if (placedEggs.getBoolean(pre + ".Season." + season)) {
                        if (counter < 3)
                            lore.add("  §7- " + season);
                        counter++;
                    }
                }
                if(counter == 0) {
                    lore.add("  §cN/A");
                }
                if(counter > 3)
                    lore.add("  §7§o+" + (counter-3) + " "+messageManager.getMessage(MessageKey.REQUIREMENTS_MORE)+"...");
            }
        } else{
            lore.add("§d"+messageManager.getMessage(MessageKey.REQUIREMENTS_NAME_SEASON)+":");
            lore.add("  §cN/A");
        }
        lore.add("");
        lore.add(messageManager.getMessage(MessageKey.REQUIREMENTS_CLICK_TO_CHANGE));
        return lore;
    }

    public boolean canBeAccessed(String collection){
        String pre = "Requirements.";
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        String currentHour = String.valueOf(LocalTime.now().getHour());
        String currentDate = DateTimeUtil.getCurrentDateString();
        String currentWeekday = String.valueOf(DateTimeUtil.getWeek(Calendar.getInstance()));
        String currentMonth = String.valueOf(DateTimeUtil.getMonth(Calendar.getInstance()));
        String currentYear = String.valueOf(DateTimeUtil.getCurrentYear());
        String currentSeason = String.valueOf(DateTimeUtil.getCurrentSeason());
        ArrayList<String> allAvailable = new ArrayList<>();
        if (placedEggs.contains("Requirements.Hours")) {
            allAvailable.addAll(placedEggs.getConfigurationSection("Requirements.Hours").getKeys(false).stream()
                    .filter(hours -> placedEggs.getBoolean(pre + ".Hours." + hours))
                    .collect(Collectors.toList()));
        }
        if (placedEggs.contains("Requirements.Date")) {
            Bukkit.broadcastMessage(String.valueOf(DateTimeUtil.getAllDaysOfYear()));
            List<String> dateList = new ArrayList<>(placedEggs.getConfigurationSection("Requirements.Date").getKeys(false));

            List<String> available = dateList.stream()
                    .filter(date -> placedEggs.getBoolean(pre + ".Date." + date))
                    .collect(Collectors.toList());

            Bukkit.broadcastMessage(currentDate);
            return available.contains(currentDate);
        }
        if (placedEggs.contains("Requirements.Weekday")) {
            allAvailable.addAll(placedEggs.getConfigurationSection("Requirements.Weekday").getKeys(false).stream()
                    .filter(weekday -> placedEggs.getBoolean(pre + ".Weekday." + weekday))
                    .collect(Collectors.toList()));
        }
        if (placedEggs.contains("Requirements.Month")) {
            allAvailable.addAll(placedEggs.getConfigurationSection("Requirements.Month").getKeys(false).stream()
                    .filter(month -> placedEggs.getBoolean(pre + ".Month." + month))
                    .collect(Collectors.toList()));
        }
        if (placedEggs.contains("Requirements.Year")) {
            allAvailable.addAll(placedEggs.getConfigurationSection("Requirements.Year").getKeys(false).stream()
                    .filter(year -> placedEggs.getBoolean(pre + ".Year." + year))
                    .collect(Collectors.toList()));
        }
        if (placedEggs.contains("Requirements.Season")) {
            allAvailable.addAll(placedEggs.getConfigurationSection("Requirements.Season").getKeys(false).stream()
                    .filter(season -> placedEggs.getBoolean(pre + ".Season." + season))
                    .collect(Collectors.toList()));
        }
        boolean isContained = allAvailable.stream().anyMatch(value ->
                value.equals(currentHour) || value.equals(currentWeekday) || value.equals(currentMonth) || value.equals(currentYear) || value.equals(currentSeason)
        );
        return isContained;
    }

    public void changeActivity(String collection, boolean active){
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        for(int i = 0; i < 24; i++){
            placedEggs.set("Requirements.Hours." + i, active);
        }
        for(String currentDate : DateTimeUtil.getAllDaysOfYear()){
            placedEggs.set("Requirements.Date." + currentDate, active);
        }
        for(String weekday : DateTimeUtil.getWeekList()){
            placedEggs.set("Requirements.Weekday." + weekday, active);
        }
        for(String month : DateTimeUtil.getMonthList()){
            placedEggs.set("Requirements.Month." + month, active);
        }
        int currentYear = DateTimeUtil.getCurrentYear();
        for (int year = currentYear; year < (currentYear + 28);year++) {
            placedEggs.set("Requirements.Year." + year, active);
        }
        for(String season : DateTimeUtil.getSeasonList()){
            placedEggs.set("Requirements.Season." + season, active);
        }
        plugin.getEggDataManager().savePlacedEggs(collection);
    }

    public void resetReset(String collection){
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        placedEggs.set("Reset.Year", 0);
        placedEggs.set("Reset.Month", 0);
        placedEggs.set("Reset.Date", 0);
        placedEggs.set("Reset.Hour", 0);
        placedEggs.set("Reset.Minute", 0);
        placedEggs.set("Reset.Second", 0);
        plugin.getEggDataManager().savePlacedEggs(collection);
    }

    public int getOverallTime(String collection){
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        int years = placedEggs.getInt("Reset.Year") * 365 * 24 * 60 * 60;
        int months = placedEggs.getInt("Reset.Month") * 30 * 24 * 60 * 60;
        int days = placedEggs.getInt("Reset.Date") * 24 * 60 * 60;
        int hours = placedEggs.getInt("Reset.Hour") * 60 * 60;
        int min = placedEggs.getInt("Reset.Minute") * 60;
        int seconds = placedEggs.getInt("Reset.Second");
        return years + months + days + hours + min + seconds;
    }

    public String getConvertedTime(String collection) {
        int years = 0;
        int months = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int duration = getOverallTime(collection);

        if (duration / (60 * 60 * 24 * 365) >= 1) {
            years = duration / (60 * 60 * 24 * 365);
            duration = duration - (years * 60 * 60 * 24 * 365);
        }
        if (duration / (60 * 60 * 24 * 30) >= 1) {
            months = duration / (60 * 60 * 24 * 30);
            duration = duration - (months * 60 * 60 * 24 * 30);
        }
        if (duration / (60 * 60 * 24) >= 1) {
            days = duration / (60 * 60 * 24);
            duration = duration - (days * 60 * 60 * 24);
        }
        if (duration / (60 * 60) >= 1) {
            hours = duration / (60 * 60);
            duration = duration - (hours * 60 * 60);
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration = duration - (minutes * 60);
        }
        if (duration >= 1) {
            seconds = duration;
        }
        if(years == 0 && months == 0 && days == 0 && hours == 0 && minutes == 0 && seconds == 0)
            return "§4§lNEVER";
        else
            return years + "Y " + months + "M " + days + "d " + hours + "h " + minutes + "m " + seconds + "s";
    }

    public void removeAllEggBlocks(String collection, UUID uuid){
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        if (!placedEggs.contains("PlacedEggs.")) {
            return;
        }
        for(String ids : placedEggs.getConfigurationSection("PlacedEggs.").getKeys(false)){
            int x = placedEggs.getInt("PlacedEggs." + ids + ".X");
            int y = placedEggs.getInt("PlacedEggs." + ids + ".Y");
            int z = placedEggs.getInt("PlacedEggs." + ids + ".Z");
            int chunkX = x >> 4;
            int chunkZ = z >> 4;
            World world = Bukkit.getWorld(Objects.requireNonNull(placedEggs.getString("PlacedEggs." + ids + ".World")));
            Chunk chunk = world.getChunkAt(chunkX, chunkZ);
            if (!chunk.load()) {
                continue;
            }
            Location location = new Location(world, x, y, z);
            Block block = location.getBlock();
            DeletionTypes deletionTypes = Main.getInstance().getPlayerEggDataManager().getDeletionType(uuid);
            switch (deletionTypes){
                case Player_Heads:
                    if(block.getType().equals(XMaterial.PLAYER_HEAD.parseMaterial()) || block.getType().equals(XMaterial.PLAYER_WALL_HEAD.parseMaterial())){
                        new Location(world, x, y, z).getBlock().setType(org.bukkit.Material.AIR);
                        plugin.getMessageManager().sendMessage(Bukkit.getConsoleSender(), MessageKey.SUCCESSFULLY_CHANGED_BLOCK,
                                "%X%", String.valueOf(x), "%Y%", String.valueOf(y), "%Z%", String.valueOf(z));
                    }
                    break;
                case Everything:
                    new Location(world, x, y, z).getBlock().setType(org.bukkit.Material.AIR);
                    plugin.getMessageManager().sendMessage(Bukkit.getConsoleSender(), MessageKey.SUCCESSFULLY_CHANGED_BLOCK,
                            "%X%", String.valueOf(x), "%Y%", String.valueOf(y), "%Z%", String.valueOf(z));
                    break;
            }
        }
        Main.getInstance().getEggManager().spawnEggParticle();
        Main.getInstance().getEggDataManager().reload();
    }

    public String getActives(Requirements requirements, String collection){
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        switch (requirements){
            case Hours:
                int hours = 0;
                for(int i = 0; i < 24; i++){
                    boolean enabled = placedEggs.getBoolean("Requirements.Hours." + i);
                    if(enabled) hours++;
                }
                return hours + "/24";
            case Date:
                int date = 0;
                for(String currentDate : DateTimeUtil.getAllDaysOfYear()){
                    boolean enabled = placedEggs.getBoolean("Requirements.Date." + currentDate);
                    if(enabled) date++;
                }
                return date + "/"+DateTimeUtil.getDaysInCurrentYear();
            case Weekday:
                int weekdays = 0;
                for(String weekday : DateTimeUtil.getWeekList()){
                    boolean enabled = placedEggs.getBoolean("Requirements.Weekday." + weekday);
                    if(enabled) weekdays++;
                }
                return weekdays + "/7";
            case Month:
                int months = 0;
                for(String month : DateTimeUtil.getMonthList()){
                    boolean enabled = placedEggs.getBoolean("Requirements.Month." + month);
                    if(enabled) months++;
                }
                return months + "/12";
            case Year:
                int years = 0;
                int currentYear = DateTimeUtil.getCurrentYear();
                for (int year = currentYear; year < (currentYear + 28);year++) {
                    boolean enabled = placedEggs.getBoolean("Requirements.Year." + year);
                    if(enabled) years++;
                }
                return years + "/28";
            case Season:
                int seasons = 0;
                for(String season : DateTimeUtil.getSeasonList()){
                    boolean enabled = placedEggs.getBoolean("Requirements.Season." + season);
                    if(enabled) seasons++;
                }
                return seasons + "/4";
        }
        return "§4ERROR";
    }

    public String getRequirementsTranslation(String translation){
        switch (translation){
            case "Winter":
                return messageManager.getMessage(MessageKey.REQUIREMENTS_SEASON_WINTER);
            case "Summer":
                return messageManager.getMessage(MessageKey.REQUIREMENTS_SEASON_SUMMER);
            case "Fall":
                return messageManager.getMessage(MessageKey.REQUIREMENTS_SEASON_FALL);
            case "Spring":
                return messageManager.getMessage(MessageKey.REQUIREMENTS_SEASON_SPRING);
            case "Monday":
                return messageManager.getMessage(MessageKey.DAY_MONDAY);
            case "Tuesday":
                return messageManager.getMessage(MessageKey.DAY_TUESDAY);
            case "Wednesday":
                return messageManager.getMessage(MessageKey.DAY_WEDNESDAY);
            case "Thursday":
                return messageManager.getMessage(MessageKey.DAY_THURSDAY);
            case "Friday":
                return messageManager.getMessage(MessageKey.DAY_FRIDAY);
            case "Saturday":
                return messageManager.getMessage(MessageKey.DAY_SATURDAY);
            case "Sunday":
                return messageManager.getMessage(MessageKey.DAY_SUNDAY);
            case "January":
                return messageManager.getMessage(MessageKey.MONTH_JANUARY);
            case "February":
                return messageManager.getMessage(MessageKey.MONTH_FEBRUARY);
            case "March":
                return messageManager.getMessage(MessageKey.MONTH_MARCH);
            case "April":
                return messageManager.getMessage(MessageKey.MONTH_APRIL);
            case "May":
                return messageManager.getMessage(MessageKey.MONTH_MAY);
            case "June":
                return messageManager.getMessage(MessageKey.MONTH_JUNE);
            case "July":
                return messageManager.getMessage(MessageKey.MONTH_JULY);
            case "August":
                return messageManager.getMessage(MessageKey.MONTH_AUGUST);
            case "September":
                return messageManager.getMessage(MessageKey.MONTH_SEPTEMBER);
            case "October":
                return messageManager.getMessage(MessageKey.MONTH_OCTOBER);
            case "November":
                return messageManager.getMessage(MessageKey.MONTH_NOVEMBER);
            case "December":
                return messageManager.getMessage(MessageKey.MONTH_DECEMBER);
        }
        return "§4NO TRANSLATION FOUND";
    }
}

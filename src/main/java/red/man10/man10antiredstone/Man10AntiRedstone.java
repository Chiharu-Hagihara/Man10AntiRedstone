package red.man10.man10antiredstone;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Man10AntiRedstone extends JavaPlugin implements Listener {

    static JavaPlugin plugin;
    static List<Material> antiblocks = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;

        plugin.saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        loadConfig();
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPutRedstone(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        if (!antiblocks.contains(e.getBlock().getType())) return;

        if (p.hasPermission("man10antirs.allow")) return;

        e.setCancelled(true);

        p.sendMessage(Component.text("§c§lあなたはそのブロックを設置する権限がありません。"));
    }

    public static void loadConfig() {
        plugin.reloadConfig();

        List<String> blocks = plugin.getConfig().getStringList("antiblocks");

        for (String s : blocks) {
            Material block = Material.valueOf(s);

            antiblocks.add(block);
        }

        plugin.getLogger().info("Config loaded.");
    }
}

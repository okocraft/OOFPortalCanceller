package net.okocraft.oofportalcanceller;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private static final Pattern NETHER_WORLD_NAME_PATTERN = Pattern.compile("(.+)_nether");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    private void onPortalCreate(PortalCreateEvent event) {
        World world = event.getWorld();
        if (world.getEnvironment() != World.Environment.NETHER || event.getBlocks().isEmpty()) {
            return;
        }

        String worldName = world.getName();
        Matcher matcher = NETHER_WORLD_NAME_PATTERN.matcher(worldName);
        if (!matcher.matches()) {
            return;
        }

        World destination = getServer().getWorld(matcher.group(1));
        if (destination == null) {
            return;
        }

        Location loc = averageLocation(destination, event.getBlocks()).multiply(8);
        if (!destination.getWorldBorder().isInside(loc)) {
            event.setCancelled(true);
        }
    }

    private static Location averageLocation(World world, List<BlockState> blocks) {
        return new Location(
                world,
                averageCoordinate(blocks, BlockState::getX),
                0,
                averageCoordinate(blocks, BlockState::getZ)
        );
    }

    private static double averageCoordinate(List<BlockState> blocks, Function<BlockState, Integer> getter) {
        return blocks.stream()
                .map(getter)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.MIN_VALUE);
    }
}

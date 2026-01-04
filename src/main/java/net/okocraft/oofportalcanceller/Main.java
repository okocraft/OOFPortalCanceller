package net.okocraft.oofportalcanceller;

import java.util.List;
import java.util.function.Function;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    private void onPortalCreate(PortalCreateEvent event) {
        World world = event.getWorld();
        String worldName = world.getName();
        if (world.getEnvironment() != World.Environment.NETHER
                || event.getBlocks().isEmpty()
                || !worldName.matches(".+_nether")) {
            return;
        }

        World destination = getServer().getWorld(worldName.substring(0, worldName.length() - 7));
        Location loc = averageLocation(world, event.getBlocks()).multiply(8);
        loc.setWorld(destination);
        if (destination != null && !destination.getWorldBorder().isInside(loc)) {
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

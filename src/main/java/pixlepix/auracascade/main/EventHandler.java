package pixlepix.auracascade.main;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import pixlepix.auracascade.block.entity.EntityBaitFairy;
import pixlepix.auracascade.block.entity.EntityDigFairy;
import pixlepix.auracascade.block.entity.EntityFairy;
import pixlepix.auracascade.block.entity.EntityScareFairy;
import pixlepix.auracascade.item.ItemFairyRing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pixlepix on 12/16/14.
 */
public class EventHandler {
    public ArrayList<EntityScareFairy> scareFairies = new ArrayList<EntityScareFairy>();

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event){
        int scareCount = 0;
        for(Entity entity: scareFairies){
            if(entity.worldObj == event.world && entity.getDistance(event.x, event.y, event.z) < 50){
                scareCount += 1;
            }
        }
        Random random = new Random();
        if(scareCount > 0){
            if(random.nextInt(25) <= scareCount){
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onGetBreakSpeed(PlayerEvent.BreakSpeed event){
        ItemStack item = BaublesApi.getBaubles(event.entityPlayer).getStackInSlot(1);
        if(event.block == Blocks.stone && item != null && item.getItem() instanceof ItemFairyRing && !event.entityPlayer.worldObj.isRemote){
            List<EntityDigFairy> fairyList = event.entityPlayer.worldObj.getEntitiesWithinAABB(EntityDigFairy.class, event.entityPlayer.boundingBox.expand(20, 20, 20));
            int count = -1;
            for(EntityDigFairy digFairy:fairyList){
                if(digFairy.player == event.entityPlayer){
                    count ++;
                }
            }
            count = Math.min(count, 15);
            if(new Random().nextInt(50) <= count){
                event.newSpeed = Float.MAX_VALUE;
            }
        }
    }



}

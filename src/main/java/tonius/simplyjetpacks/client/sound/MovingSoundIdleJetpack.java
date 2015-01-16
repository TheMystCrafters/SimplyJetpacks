package tonius.simplyjetpacks.client.sound;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tonius.simplyjetpacks.SimplyJetpacks;
import tonius.simplyjetpacks.SyncTracker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSoundIdleJetpack extends MovingSound {
    
    private static final ResourceLocation SOUND = new ResourceLocation(SimplyJetpacks.RESOURCE_PREFIX + "jetpack_flight_idle");
    public static Set<Integer> playingFor = Collections.synchronizedSet(new HashSet<Integer>());
    
    private EntityLivingBase user;
    
    public MovingSoundIdleJetpack(EntityLivingBase target) {
        super(SOUND);
        this.repeat = true;
        this.user = target;
        playingFor.add(target.getEntityId());
    }
    
    @Override
    public void update() {
        ItemStack armor = this.user.getEquipmentInSlot(3);
        if (!SyncTracker.getJetpackStates().keySet().contains(this.user.getEntityId())) {
            this.donePlaying = true;
            synchronized (playingFor) {
                playingFor.remove(this.user.getEntityId());
            }
        }
        
        this.xPosF = (float) this.user.posX;
        this.yPosF = (float) this.user.posY;
        this.zPosF = (float) this.user.posZ;
    }
    
}

package com.badgerson.larion.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

// @Mixin(SurfaceBuilder.class)
// public class SurfaceBuilderMixin {
//   @ModifyReturnValue(method = "applyMaterialRule", at=@At("RETURN"))
//   Optional<BlockState> larion$applyMaterialRule(Optional<BlockState> original, @Local CarverContext context) {
//     return original.map((state) -> {
//     });
//   } 
// }

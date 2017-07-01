package im.octo.jungletree.rainforest.minecraft.world;

public interface Block {

    BlockType getType();

    void setType(BlockType type);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getZ();

    void setZ(int z);
}

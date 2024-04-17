package com.helaoui.abdallah.tondeuse.batch;

import com.helaoui.abdallah.tondeuse.model.Tondeuse;
import org.springframework.batch.item.ItemProcessor;
import com.helaoui.abdallah.tondeuse.model.Pelouse ;


public class TondeuseItemProcessor implements ItemProcessor<Tondeuse, Tondeuse> {

    @Override
    public Tondeuse process(Tondeuse tondeuse) throws Exception {
        for (char instruction : tondeuse.getInstructions().toCharArray()) {
            if (instruction == 'A') {
                moveForward(tondeuse);
            } else {
                rotateTondeuse(tondeuse, instruction);
            }
        }
        System.out.println("tenseuse final postion is :" + tondeuse);
        return tondeuse;
    }


    private void moveForward(Tondeuse tondeuse) {
        switch (tondeuse.getOrientation()) {

                case 'N':
                    if (tondeuse.getY() < Pelouse.getTopRightY()) {
                        tondeuse.setY(tondeuse.getY() + 1);
                    }
                    break;
                case 'E':
                    if (tondeuse.getX() < Pelouse.getTopRightX()) {
                        tondeuse.setX(tondeuse.getX() + 1);
                    }
                    break;
                case 'S':
                    if (tondeuse.getY() > 0) {
                        tondeuse.setY(tondeuse.getY() - 1);
                    }
                    break;
                case 'W':
                    if (tondeuse.getX() > 0) {
                        tondeuse.setX(tondeuse.getX() - 1);
                    }
                    break;
            }
        }


    private void rotateTondeuse(Tondeuse tondeuse, char direction) {
        if (direction == 'D') {
            switch (tondeuse.getOrientation()) {
                case 'N':
                    tondeuse.setOrientation('E');
                    break;
                case 'E':
                    tondeuse.setOrientation('S');
                    break;
                case 'S':
                    tondeuse.setOrientation('W');
                    break;
                case 'W':
                    tondeuse.setOrientation('N');
                    break;
            }
        } else if (direction == 'G') {
            switch (tondeuse.getOrientation()) {
                case 'N':
                    tondeuse.setOrientation('W');
                    break;
                case 'E':
                    tondeuse.setOrientation('N');
                    break;
                case 'S':
                    tondeuse.setOrientation('E');
                    break;
                case 'W':
                    tondeuse.setOrientation('S');
                    break;
            }
        }
    }

}

package ru.vsu.amm.java;

import java.util.Objects;

    public class Axolotl extends  Fish{
        private final boolean canRegenerate;

        public Axolotl(String name, double weight, boolean canRegenerate) {
            super(name, weight);
            this.canRegenerate = canRegenerate;
        }

        @Override
        public void swim() {
            System.out.println( name + " шевеля лапками, медленно плывет. ");
        }

        @Override
        public void eat() {
            System.out.println(name + " любит есть червей и мелкую рыбку.");
        }

        public void regenerate() {
            if (canRegenerate) {
                System.out.println(name + " сможет восстановить потерянную лапку");
            } else {
                System.out.println(name + " уже не получится регенерировать лапку.");
            }
        }

         @Override
         public String toString() {
                return super.toString() + ", способность к регенерации: " +  (canRegenerate ? "ДА" : "НЕТ" );
         }


         @Override
         public boolean equals(Object o) {
            if (!super.equals(o)) return false;
            Axolotl axolotl = (Axolotl) o;
            return canRegenerate == axolotl.canRegenerate;
         }

        @Override
        public int hashCode() {
            return Objects.hash(name, weight, canRegenerate);
        }

        @Override
        public String getDescription() {
            return name + " - аквариумный дракон, который может жить на суше и в воде.";
        }

    }




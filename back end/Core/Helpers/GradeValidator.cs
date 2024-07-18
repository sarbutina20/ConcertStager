namespace Core.Helpers
{
    public static class GradeValidator
    {
        public static bool isGradeValid(float grade)
        {
            return grade >= 1 && grade <= 5;
        }
    }
}

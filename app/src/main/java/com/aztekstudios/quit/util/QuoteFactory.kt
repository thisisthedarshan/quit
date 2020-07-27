/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "QuoteFactory.kt" is a part of the project "Quit"                                           *
 *                                                                                                          *
 *     Quit is free software: you can redistribute it and/or modify                                         *
 *     it under the terms of the GNU General Public License as published by                                 *
 *     the Free Software Foundation, either version 3 of the License, or                                    *
 *     (at your option) any later version.                                                                  *
 *                                                                                                          *
 *     Project Quit is distributed in the hope that it will be useful,                                      *
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of                                       *
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                        *
 *     GNU General Public License for more details.                                                         *
 *                                                                                                          *
 *     You should have received a copy of the GNU General Public License                                    *
 *     along with Project Quit.  If not, see <https://www.gnu.org/licenses/>.                               *
 *                                                                                                          *
 ************************************************************************************************************/

package com.aztekstudios.quit.util

import kotlin.random.Random

/**
 * A method to generate random quotes
 */
class QuoteFactory {
    /**
     * Returns a randomly generated quote from the quotes list
     * @return Quote
     */
    fun getRandomQuote(): String {
        return quotes[Random.nextInt(quotes.size)] // Random quote is generated using random function
    }

    /**
     * Quotes go here. New ones can be added
     */
    private val quotes = arrayOf(
        "Health is the greatest gift, contentment the greatest wealth, faithfulness the best relationship.  ~Buddha",
        "A healthy attitude is contagious but don’t wait to catch it from others.” Be a carrier.   ~Unknown",
        "Ill-health of body or of mind, is defeat. Health alone is victory. Let all men, if they can manage it, contrive to be healthy!  ~Thomas Carlyle",
        "The best six doctors anywhere and no one can deny it are sunshine, water, rest, air, exercise and diet.  ~Wayne Fields",
        "Calm mind brings inner strength and self-confidence, so that’s very important for good health.  ~ Dalai Lama",
        "There’s nothing more important than our good health – that’s our principal capital asset.  ~Arlen Specter",
        "The wish for healing has always been half of health.  ~Lucius Annaeus Seneca",
        "No matter how much it gets abused, the body can restore balance. The first rule is to stop interfering with nature.  ~Deepak Chopra",
        "To keep the body in good health is a duty… otherwise, we shall not be able to keep our mind strong and clear.  ~Buddha",
        "The greatest wealth is health.  ~Virgil",
        "I believe that the greatest gift you can give your family and the world is a healthy you.  ~Joyce Meyer",
        "Cheerfulness is the best promoter of health and is as friendly to the mind as to the body.  ~Joseph Addison",
        "To get rich never your risk your health. For it is the truth that health is the wealth of wealth.  ~Richard Baker",
        "You are not your illness. You have an individual story to tell. You have a name, a history, a personality. Staying yourself is part of the battle.  ~Julian Seifte ",
        "Rest when you’re weary. Refresh and renew yourself, your body, your mind, your spirit. Then get back to work.  ~Ralph Marston",
        "To insure good health: Eat lightly, breathe deeply, live moderately, cultivate cheerfulness, and maintain an interest in life.  ~William Londen",
        "The natural healing force in each one of us is the greatest force in getting well.  ~Hippocrates",
        "To enjoy the glow of good health, you must exercise.  ~Gene Tunney",
        "A healthy outside starts from the inside.  ~Robert Urich",
        "The sovereign invigorator of the body is exercise, and of all the exercises walking is the best.  ~Thomas Jefferson",
        "Nurturing yourself is not selfish, it’s essential to your survival and your well-being.  ~Renee Peterson Trudeau",
        "What the public expects and what is healthy for an individual are two very different things.  ~Esther Williams",
        "I thought: If I was lucky enough to live, I’d change myself. I realized I could have a new life, new energy, new endurance, and feel better about myself.  ~Larry King",
        "Life is like riding a bicycle. To keep your balance, you must keep moving.  ~Albert Einstein",
        "It is health that is real wealth and not pieces of gold and silver.  ~Mahatma Gandhi",
        "When wealth is lost, nothing is lost; when health is lost, something is lost; when character is lost, all is lost.  ~Billy Graham",
        "It is no measure of health to be well adjusted to a profoundly sick society.  ~Jiddu Krishnamurti",
        "Before thirty, men seek disease. After thirty, disease seeks men.  ~Chinese Proverb",
        "Your body will be around a lot longer than that expensive handbag. Invest in yourself.  ~Unknown",
        "Sleep is that golden chain that ties health and our bodies together.  ~Thomas Dekker",
        "A healthy family is sacred territory.  ~Anonymous",
        "The food you eat can either be the safest & most powerful form of medicine…or the slowest form of poison.  ~Ann Wigmore",
        "He who has health, has hope; and he who has hope, has everything.  ~Thomas Carlyle",
        "Embrace and love your body. It’s the most amazing thing you will ever own.  ~D",
        "Good health is not something we can buy. However, it can be an extremely valuable savings account.  ~Anne Wilson Schaef",
        "Health is the greatest of all possessions; a pale cobbler is better than a sick king.  ~Isaac Bickerstaff",
        "Good health and good sense are two of life’s greatest blessings.  ~Publilius Syrus",
        "Give a man health and a course to steer, and he’ll never stop to trouble about whether he’s happy or not.  ~George Bernard Shaw",
        "Your body hears everything your mind says.  ~Naomi Judd",
        "A wise man should consider that health is the greatest of human blessings, and learn how by his own thought to derive benefit from his illnesses.  ~Hippocrates",
        "Lost wealth may be replaced by industry, lost knowledge by study, lost health by temperance or medicine, but lost time is gone forever.  ~Samuel Smiles",
        "Slow Progress is better than No Progress.  ~Darshan",
        "Being challenged in life is inevitable. Being defeated is optional.  ~Unknown",
        "Success is the sum of all small efforts repeated Day-In and Day-Out.  ~Robert Collier",
        "Health is not valued till sickness comes.  ~Thomas Fuller",
        "A healthy mind does not speak ill of others.  ~Anonymous",
        "If it came from a plant, eat it. If it was made in a plant, DON'T!  ~Michael Pollan",
        "Don't judge each day by the harvest you reap but by the seeds that you plant.  ~Robert Louis Stevenson",
        "Everyone thinks of changing the world, but no one thinks of changing himself.  ~Leo Tolstoy",
        "The world as we have created it is a process of our thinking. It cannot be changed without changing our thinking.  ~Albert Einstein",
        "True life is lived when tiny changes occur.”  ~Leo Tolstoy",
        "Perfection is not attainable, but if we chase perfection we can catch excellence.  ~Vince Lombardi"
    )
}